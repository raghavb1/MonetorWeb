package com.champ.gmail.api.response;

public class GmailTokensResponse {

	private String access_token;
	private String refresh_token;
	private String email;
	private Integer gmailTokenExpirySeconds;

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

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public Integer getGmailTokenExpirySeconds() {
		return gmailTokenExpirySeconds;
	}

	public void setGmailTokenExpirySeconds(Integer gmailTokenExpirySeconds) {
		this.gmailTokenExpirySeconds = gmailTokenExpirySeconds;
	}

	@Override
	public String toString() {
		return "GmailTokensResponse [access_token=" + access_token + ", refresh_token=" + refresh_token + ", email="
				+ email + ", gmailTokenExpirySeconds=" + gmailTokenExpirySeconds + "]";
	}

}
