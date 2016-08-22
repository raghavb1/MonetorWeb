package com.champ.services.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.champ.core.cache.PropertyMapCache;
import com.champ.core.entity.AppUserLinkedAccount;
import com.champ.core.entity.Bank;
import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.data.access.services.IAppUserLinkedAccountDao;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.MessageListResponse;
import com.champ.gmail.api.response.MessageResponse;
import com.champ.gmail.api.response.RefreshTokenResponse;
import com.champ.gmail.api.service.Convertor;
import com.champ.gmail.api.service.Helper;
import com.champ.gmail.api.service.URLGeneratorService;
import com.champ.services.IAppUserBankService;
import com.champ.services.IGmailClientService;
import com.champ.services.ITransactionService;
import com.champ.services.thread.SaveTransactionThread;

@Service
public class GmailClientServiceImpl implements IGmailClientService {

	@Autowired
	URLGeneratorService urlGenerator;

	@Autowired
	Helper helper;

	@Autowired
	Convertor convertor;

	@Autowired
	IAppUserLinkedAccountDao appUserLinkedAccountDao;

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	ITransactionService transactionService;

	@Autowired
	IAppUserBankService appUserBankService;

	private static final Logger LOG = LoggerFactory.getLogger(GmailClientServiceImpl.class);

	public String getAuthURL() throws URISyntaxException {
		return urlGenerator.getAuthURL().toString();
	}

	public GmailTokensResponse getTokensFromCode(String code)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = urlGenerator.getTokenURL();
		List<NameValuePair> urlParameters = urlGenerator.getTokenQueryParameters(code);
		StringBuffer sb = helper.executePost(uri.toString(), urlParameters);
		return (GmailTokensResponse) helper.getObjectFromJsonString(sb, GmailTokensResponse.class);
	}

	public RefreshTokenResponse refreshAccessToken(String refreshToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = urlGenerator.getTokenURL();
		List<NameValuePair> urlParameters = urlGenerator.getRefreshTokenQueryParameters(refreshToken);
		HttpGet httpget = new HttpGet(uri);
		StringBuffer sb = helper.executePost(httpget.getURI().toString(), urlParameters);
		return ((RefreshTokenResponse) helper.getObjectFromJsonString(sb, RefreshTokenResponse.class));
	}

	public MessageListResponse getMessageList(String userId, String searchQuery, String accessToken, String pageToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = urlGenerator.getMessageListURL(userId, searchQuery, accessToken, pageToken);
		StringBuffer sb = helper.executeGet(uri.toString());
		return (MessageListResponse) helper.getObjectFromJsonString(sb, MessageListResponse.class);
	}

	public MessageResponse getMessage(String userId, String messageId, String accessToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = urlGenerator.getMessageURL(userId, messageId, accessToken);
		StringBuffer sb = helper.executeGet(uri.toString());
		return (MessageResponse) helper.getObjectFromJsonString(sb, MessageResponse.class);
	}

	public static String base64UrlDecode(String input) {
		String result = null;
		Base64 decoder = new Base64(true);
		byte[] decodedBytes = decoder.decode(input);
		result = new String(decodedBytes);
		return result;
	}

	public TransactionDTO getTransactionDetailsFromEmail(MessageResponse messageResponse, String pattern,
			String dateFormat) throws ParseException {
		TransactionDTO transaction = null;
		String input = null;
		if (messageResponse.getPayload().getParts() != null) {
			input = messageResponse.getPayload().getParts().get(0).getBody().getData();
		} else if (messageResponse.getPayload().getBody() != null) {
			input = messageResponse.getPayload().getBody().getData();
		}
		input = base64UrlDecode(input);
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(input);
		if (m.find()) {
			transaction = convertor.getTransactionDTOFromMessage(m, dateFormat);
		}
		if (transaction != null && transaction.getDate() == null) {
			transaction.setDate(new Date(Long.parseLong(messageResponse.getInternalDate())));
		}
		return transaction;
	}

	@Transactional
	public List<TransactionDTO> getMessages(AppUserLinkedAccount account, SearchQuery searchQuery, Parser parser,
			Bank bank) throws Exception {
		long startTime = System.currentTimeMillis();
		List<TransactionDTO> transactionDto = new ArrayList<TransactionDTO>();
		String accessToken = account.getAccessToken();
		RefreshTokenResponse tokenResponse = null;
		if (DateUtils.isDatePassed(account.getGmailExpiryTime())) {
			tokenResponse = refreshAccessToken(account.getRefreshToken());
			if (tokenResponse == null) {
				return null;
			}
			accessToken = tokenResponse.getAccess_token();
		}
		String pageToken = null;
		do {
			MessageListResponse list = getMessageList(account.getEmail(), searchQuery.getSearchQuery(), accessToken,
					pageToken);
			if (list != null && list.getMessages() != null && list.getMessages().size() > 0) {
				int batchSize = CacheManager.getInstance().getCache(PropertyMapCache.class)
						.getPropertyInteger(Property.TRANSACTION_BATCH_SIZE);
				for (MessageListResponse.Message message : list.getMessages()) {
					MessageResponse messageResponse = getMessage(account.getEmail(), message.getId(), accessToken);
					TransactionDTO dto = getTransactionDetailsFromEmail(messageResponse,
							StringEscapeUtils.unescapeJava(parser.getTemplate()),
							StringEscapeUtils.unescapeJava(parser.getDateFormat()));
					if (dto != null) {
						transactionDto.add(dto);
					}
					if (transactionDto.size() == batchSize) {
						LOG.info("Assigning Save transaction thread to executor");
						taskExecutor.submit(new SaveTransactionThread(account.getUser(), transactionDto, bank,
								transactionService, appUserBankService));
						transactionDto = new ArrayList<TransactionDTO>();
					}
				}
				if (transactionDto != null && transactionDto.size() > 0) {
					taskExecutor.submit(new SaveTransactionThread(account.getUser(), transactionDto, bank,
							transactionService, appUserBankService));
				}
				pageToken = list.getNextPageToken();
			} else {
				pageToken = null;
			}
		} while (pageToken != null);
		if (tokenResponse != null) {
			account.setAccessToken(accessToken);
			account.setGmailExpiryTime(
					DateUtils.addToDate(new Date(), TimeUnit.SECONDS, tokenResponse.getExpires_in()));
			account = appUserLinkedAccountDao.saveOrUpdateLinkedAccount(account);
		}
		long endTime = System.currentTimeMillis();
		LOG.info("Time taken to get messages from Gmail {} ms", (endTime - startTime));
		return transactionDto;
	}

}
