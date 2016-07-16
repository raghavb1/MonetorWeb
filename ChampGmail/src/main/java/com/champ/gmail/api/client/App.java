package com.champ.gmail.api.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.MessageListResponse;
import com.champ.gmail.api.response.MessageResponse;
import com.champ.gmail.api.response.RefreshTokenResponse;
import com.champ.gmail.api.service.Convertor;
import com.champ.gmail.api.service.Helper;
import com.champ.gmail.api.service.URLGeneratorService;

/**
 * Hello world!
 *
 */
public class App 
{
	URLGeneratorService urlGenerator = new URLGeneratorService();
	Helper helper = new Helper();
	Convertor convertor = new Convertor();
	
	public String getAuthURL() throws URISyntaxException{

		return urlGenerator.getAuthURL().toString();

	}

	public GmailTokensResponse getTokensFromCode(String code) throws URISyntaxException, ClientProtocolException, IOException{


		URI uri = urlGenerator.getTokenURL();
		List<NameValuePair> urlParameters = urlGenerator.getTokenQueryParameters(code);

		StringBuffer sb = helper.executePost(uri.toString(), urlParameters);

		return (GmailTokensResponse) helper.getObjectFromJsonString(sb, GmailTokensResponse.class);

	}

	public RefreshTokenResponse refreshAccessToken(String refreshToken) throws URISyntaxException, ClientProtocolException, IOException{

		URI uri = urlGenerator.getTokenURL();
		List<NameValuePair> urlParameters = urlGenerator.getRefreshTokenQueryParameters(refreshToken);
		HttpGet httpget = new HttpGet(uri);

		StringBuffer sb = helper.executePost(httpget.getURI().toString(), urlParameters);

		return ((RefreshTokenResponse) helper.getObjectFromJsonString(sb, RefreshTokenResponse.class));

	}

	public MessageListResponse getMessageList(String userId, String searchQuery, String accessToken) throws URISyntaxException, ClientProtocolException, IOException{

		URI uri = urlGenerator.getMessageListURL(userId, searchQuery, accessToken);
		StringBuffer sb = helper.executeGet(uri.toString());
		return (MessageListResponse) helper.getObjectFromJsonString(sb, MessageListResponse.class);
	}

	public MessageResponse getMessage(String userId, String messageId, String accessToken) throws URISyntaxException, ClientProtocolException, IOException{

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

	public TransactionDTO getTransactionDetailsFromEmail(MessageResponse messageResponse, String pattern) throws ParseException{
		TransactionDTO transaction = null;
		String input = null;

		if(messageResponse.getPayload().getParts() != null){
			input = messageResponse.getPayload().getParts().get(0).getBody().getData();
		} else if(messageResponse.getPayload().getBody() != null){
			input = messageResponse.getPayload().getBody().getData();
		}

		input = base64UrlDecode(input);
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(input);
		
		if(m.find()){
			transaction = convertor.getTransactionDTOFromMessage(m);
		}


		return transaction;

	}

	public static void main( String[] args ) throws URISyntaxException, ClientProtocolException, IOException, ParseException
	{
		App api = new App();
		String username = "deepanshu18march@gmail.com";
		String refreshToken = "1/kt6dL3_j5QupdcCMPNnz_XtDnSOB11l_a3I-ml8BF7M";
		String searchQuery = "from :alert@icicibank.com";
		String pattern = "transaction of INR (?<amount>.*) Info\\.(?<transactionType>.*)\\*(?<merchant>.*) on (?<date>.*). The available balance in your Account is Rs. (?<balance>.*)\\.";
			
		String accessToken = api.refreshAccessToken(refreshToken).getAccessToken();
		MessageListResponse list = api.getMessageList("deepanshu18march@gmail.com", searchQuery, accessToken);
	
		for(MessageListResponse.Message message : list.getMessages()){
			MessageResponse messageResponse = api.getMessage(username, message.getId(), accessToken);
			System.out.println(api.getTransactionDetailsFromEmail(messageResponse,pattern));
			System.out.println("--------------------------------");
//			break;
		}
	}
}
