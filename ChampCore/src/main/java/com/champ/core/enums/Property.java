package com.champ.core.enums;

public enum Property {

	ENABLE_CACHE										("enable.cache","true"),
	CACHE_RELOAD_PASSWORD								("cache.reload.password","testing"),
	API_AUTHENTICATION_KEY								("api.authentication.key","Entering"),
	CACHE_RELOAD_PERIOD									("cache.reload.period", "60"),
	GMAIL_REDIRECT_URL									("gmail.redirect.url", "http://localhost:8080/Monetor/service/signupCallback");

	private String name;

	private String value;

	private Property(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
