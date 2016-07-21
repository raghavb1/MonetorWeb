package com.champ.core.enums;

public enum Property {

	ENABLE_CACHE										("enable.cache","true"),
	CACHE_RELOAD_PASSWORD								("cache.reload.password","testing"),
	API_AUTHENTICATION_KEY								("api.authentication.key","Entering"),
	CACHE_RELOAD_PERIOD									("cache.reload.period", "60"),
	GMAIL_REDIRECT_URL									("gmail.redirect.url", "http://monetor.in:8080/Monetor/service/signupCallback"),
	TOKEN_EXPIRY_UPDATE_SECONDS							("token.expiry.update.seconds", "3600"),
	ENABLE_MESSAGE_TASK									("enable.message.task", "true"),
	TRANSACTION_EXECUTOR_CORE_THREAD_POOL				("transaction.executor.core.thread.pool","5"),
	TRANSACTION_EXECUTOR_MAX_THREAD_POOL				("transaction.executor.max.thread.pool","10"),
	TRANSACTION_EXECUTOR_BLOCKING_QUEUE_SIZE			("transaction.executor.blocking.queue.size","10"),
	TRANSACTION_EXECUTOR_KEEP_ALIVE_TIME				("transaction.executor.keep.alive.time","5000"),
	TRANSACTION_EXECUTOR_REJECTION_TIME					("transaction.executor.rejection.time","5000"),
	TRANSACTION_FETCH_USER_BATCH						("transaction.fetch.user.batch","5"),
	MESSAGES_FETCH_PERIOD								("messages.fetch.period","15"),
	GMAIL_SCOPE											("gmail.scope","https://www.googleapis.com/auth/gmail.readonly https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"),
	SUBMERCHANT_FETCH_LIMIT								("submerchant.fetch.limit","500"),
	USER_DISPLAY_BANKS									("user~display.banks","ICICI,HDFC");

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
