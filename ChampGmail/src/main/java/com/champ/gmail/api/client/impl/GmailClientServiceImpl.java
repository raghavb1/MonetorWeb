package com.champ.gmail.api.client.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUser;
import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.data.access.services.IAppUserDao;
import com.champ.gmail.api.client.IGmailClientService;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.MessageListResponse;
import com.champ.gmail.api.response.MessageResponse;
import com.champ.gmail.api.response.RefreshTokenResponse;
import com.champ.gmail.api.service.Convertor;
import com.champ.gmail.api.service.Helper;
import com.champ.gmail.api.service.URLGeneratorService;

@Service
public class GmailClientServiceImpl implements IGmailClientService {

	@Autowired
	URLGeneratorService urlGenerator;

	@Autowired
	Helper helper;

	@Autowired
	Convertor convertor;

	@Autowired
	IAppUserDao appUserDao;

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

	public MessageListResponse getMessageList(String userId, String searchQuery, String accessToken,String pageToken)
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
			if(transaction.getDate() == null){
				transaction.setDate(new Date(Long.parseLong(messageResponse.getInternalDate())));
			}
		}
		return transaction;
	}

	@Transactional
	public List<TransactionDTO> getMessages(AppUser user, SearchQuery searchQuery, Parser parser) throws Exception {
		List<TransactionDTO> transactionDto = new ArrayList<TransactionDTO>();
		String accessToken = user.getAccessToken();
		if (DateUtils.isDatePassed(user.getGmailExpiryTime())) {
			RefreshTokenResponse tokenResponse = refreshAccessToken(user.getRefreshToken());
			if (tokenResponse == null) {
				return null;
			}
			accessToken = tokenResponse.getAccess_token();
			user.setAccessToken(accessToken);
			user.setGmailExpiryTime(DateUtils.addToDate(new Date(), TimeUnit.SECONDS, tokenResponse.getExpires_in()));
			user = appUserDao.saveOrUpdateUser(user);
		}
		String pageToken = null;
		do{
			MessageListResponse list = getMessageList(user.getEmail(), searchQuery.getSearchQuery(), accessToken, pageToken);
			if (list != null && list.getMessages() != null && list.getMessages().size() > 0) {
				for (MessageListResponse.Message message : list.getMessages()) {
					MessageResponse messageResponse = getMessage(user.getEmail(), message.getId(), accessToken);
					TransactionDTO dto = getTransactionDetailsFromEmail(messageResponse,
							StringEscapeUtils.unescapeJava(parser.getTemplate()),
							StringEscapeUtils.unescapeJava(parser.getDateFormat()));
					if (dto != null) {
						transactionDto.add(dto);
					}
				}
				pageToken = list.getNextPageToken();
			}else{
				pageToken = null;
			}
		}while(pageToken != null);

		return transactionDto;
	}

}
