package com.champ.base.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SigninRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2281387831650062750L;

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SigninRequest [password=" + password + ", toString()=" + super.toString() + "]";
	}

}
