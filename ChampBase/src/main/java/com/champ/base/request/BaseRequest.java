package com.champ.base.request;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -582815177272317993L;
	private String email;
	private String authenticationKey;
	private String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	@Override
	public String toString() {
		return "BaseRequest [email=" + email + ", authenticationKey=" + authenticationKey + ", token=" + token + "]";
	}

}
