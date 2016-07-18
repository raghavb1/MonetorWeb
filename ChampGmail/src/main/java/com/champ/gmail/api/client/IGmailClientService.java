package com.champ.gmail.api.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.champ.core.entity.AppUser;
import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.MessageListResponse;
import com.champ.gmail.api.response.MessageResponse;
import com.champ.gmail.api.response.RefreshTokenResponse;

public interface IGmailClientService {

	public String getAuthURL() throws URISyntaxException;

	public GmailTokensResponse getTokensFromCode(String code)
			throws URISyntaxException, ClientProtocolException, IOException;

	public RefreshTokenResponse refreshAccessToken(String refreshToken)
			throws URISyntaxException, ClientProtocolException, IOException;

	public MessageListResponse getMessageList(String userId, String searchQuery, String accessToken)
			throws URISyntaxException, ClientProtocolException, IOException;

	public MessageResponse getMessage(String userId, String messageId, String accessToken)
			throws URISyntaxException, ClientProtocolException, IOException;

	public TransactionDTO getTransactionDetailsFromEmail(MessageResponse messageResponse, String pattern, String dateFormat)
			throws ParseException;

	public List<TransactionDTO> getMessages(AppUser user, SearchQuery query, Parser parser) throws Exception;

}
