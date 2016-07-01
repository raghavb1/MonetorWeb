package com.champ.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "app_user")
public class AppUser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3053363679048869253L;

	@Column(name = "email")
	private String email;

	@Column(name = "token")
	private String token;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "token_expiry_time", nullable = false)
	private Date tokenExpiryTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmail_expiry_time", nullable = false)
	private Date gmailExpiryTime;

	public Date getTokenExpiryTime() {
		return tokenExpiryTime;
	}

	public void setTokenExpiryTime(Date tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}

	public Date getGmailExpiryTime() {
		return gmailExpiryTime;
	}

	public void setGmailExpiryTime(Date gmailExpiryTime) {
		this.gmailExpiryTime = gmailExpiryTime;
	}

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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
