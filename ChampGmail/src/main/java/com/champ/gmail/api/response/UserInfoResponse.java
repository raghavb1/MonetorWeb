package com.champ.gmail.api.response;

public class UserInfoResponse {
	
	private String id;
	private String email;
	private String verified_email;
	private String name;
	private String picture;
	private String gender;
	private String locale;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVerified_email() {
		return verified_email;
	}
	public void setVerified_email(String verified_email) {
		this.verified_email = verified_email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	@Override
	public String toString() {
		return "UserInfoResponse [id=" + id + ", email=" + email + ", verified_email=" + verified_email + ", name="
				+ name + ", picture=" + picture + ", gender=" + gender + ", locale=" + locale + "]";
	}
	
	
}
