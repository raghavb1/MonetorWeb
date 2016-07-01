package com.champ.gmail.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("helper")
public class Helper {

	private final String USER_AGENT = "Mozilla/5.0";

	public StringBuffer executePost(String url, List<NameValuePair> urlParameters)
			throws ClientProtocolException, IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", USER_AGENT);
		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);

		return getStringBufferFromHttpResponse(response);
	}

	public StringBuffer executeGet(String url) throws ClientProtocolException, IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);

		return getStringBufferFromHttpResponse(response);
	}

	private StringBuffer getStringBufferFromHttpResponse(HttpResponse response)
			throws IllegalStateException, IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getObjectFromJsonString(StringBuffer buffer, Class clazz) {
		Gson gson = new GsonBuilder().create();

		JsonObject jsonObject = (new JsonParser()).parse(buffer.toString()).getAsJsonObject();
		Object output = gson.fromJson(jsonObject, clazz);
		return output;
	}
}
