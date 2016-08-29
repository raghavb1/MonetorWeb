package com.champ.core.enums;

public enum Medium {

	SMS("Sms"), EMAIL("Email");

	private String code;

	private Medium(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
