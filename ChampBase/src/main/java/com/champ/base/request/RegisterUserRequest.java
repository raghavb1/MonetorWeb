package com.champ.base.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5308051813839860709L;

	private String countryCode;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "RegisterUserRequest [countryCode=" + countryCode + "]";
	}

}
