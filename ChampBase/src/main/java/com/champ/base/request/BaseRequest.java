package com.champ.base.request;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -582815177272317993L;
	private String mobile;
	private String authenticationKey;
	private String token;

	public String getAuthenticationKey() {
		return authenticationKey;
	}

	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BaseRequest(String mobile, String authenticationKey, String token) {
		super();
		this.mobile = mobile;
		this.authenticationKey = authenticationKey;
		this.token = token;
	}

	public BaseRequest() {
		super();
	}

	@Override
	public String toString() {
		return "BaseRequest [mobile=" + mobile + ", authenticationKey=" + authenticationKey + ", token=" + token + "]";
	}

}