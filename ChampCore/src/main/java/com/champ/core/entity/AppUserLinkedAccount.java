package com.champ.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;

@Entity
@Table(name = "app_user_linked_accounts")
public class AppUserLinkedAccount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3457491755566371662L;

	@Column(name = "email")
	private String email;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "refresh_token")
	private String refreshToken;

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

	@Column(name = "synced")
	private Boolean synced = false;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private AppUser user;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getGmailExpiryTime() {
		return gmailExpiryTime;
	}

	public void setGmailExpiryTime(Date gmailExpiryTime) {
		this.gmailExpiryTime = gmailExpiryTime;
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

	public Boolean getSynced() {
		return synced;
	}

	public void setSynced(Boolean synced) {
		this.synced = synced;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AppUserLinkedAccounts [email=" + email + ", accessToken=" + accessToken + ", refreshToken="
				+ refreshToken + ", gmailExpiryTime=" + gmailExpiryTime + ", lastSyncedOn=" + lastSyncedOn + ", name="
				+ name + ", image=" + image + ", synced=" + synced + "]";
	}

}
