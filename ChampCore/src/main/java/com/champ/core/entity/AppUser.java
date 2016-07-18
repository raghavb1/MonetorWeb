package com.champ.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_synced_on", nullable = false)
	private Date lastSyncedOn = DateUtils.addToDate(new Date(), TimeUnit.DAY, -60);

	@Column(name = "name")
	private String name;

	@Column(name = "image")
	private String image;

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

	public Date getLastSyncedOn() {
		return lastSyncedOn;
	}

	public void setLastSyncedOn(Date lastSyncedOn) {
		this.lastSyncedOn = lastSyncedOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "AppUser [email=" + email + ", token=" + token + ", accessToken=" + accessToken + ", refreshToken="
				+ refreshToken + ", tokenExpiryTime=" + tokenExpiryTime + ", gmailExpiryTime=" + gmailExpiryTime
				+ ", lastSyncedOn=" + lastSyncedOn + ", name=" + name + ", image=" + image + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
