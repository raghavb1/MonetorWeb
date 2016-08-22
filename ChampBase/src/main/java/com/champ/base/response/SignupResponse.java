package com.champ.base.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 52232429289735226L;
	private String email;
	private String name;
	private String image;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return "SignupResponse [email=" + email + ", name=" + name + ", image=" + image + "]";
	}

}
