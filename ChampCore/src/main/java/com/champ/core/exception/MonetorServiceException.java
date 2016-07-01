package com.champ.core.exception;

import com.champ.core.enums.ApiResponseCodes;

public class MonetorServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4055148283693713017L;

	private String code;
	private String message;

	public MonetorServiceException() {
		super();
	}

	public MonetorServiceException(ApiResponseCodes responseCode) {
		this.code = responseCode.getCode();
		this.message = responseCode.getMessage();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
