package com.champ.base.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserSessionRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7456783161928372598L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
