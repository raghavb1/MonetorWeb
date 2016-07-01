package com.champ.gmail.api.response;

public class GmailTokensResponse {

	private String access_token;
	private String refresh_token;
	private String email;

	public String getAccessToken() {
		return access_token;
	}

	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	public String getRefreshToken() {
		return refresh_token;
	}

	public void setRefereshToken(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "GmailTokensResponse [access_token=" + access_token + ", refresh_token=" + refresh_token + ", email="
				+ email + "]";
	}

}
