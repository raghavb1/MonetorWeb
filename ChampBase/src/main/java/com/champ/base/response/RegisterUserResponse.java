package com.champ.base.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8219899268174705733L;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "RegisterUserResponse [token=" + token + "]";
	}

}
