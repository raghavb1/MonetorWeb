package com.champ.base.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 52232429289735226L;
	private String email;
	private String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "SignupResponse [email=" + email + ", token=" + token + ", toString()=" + super.toString() + "]";
	}

}
