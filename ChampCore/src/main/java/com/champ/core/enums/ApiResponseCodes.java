package com.champ.core.enums;

public enum ApiResponseCodes {

	SUCCESS												("SS","Request Processed"),
	INVALID_REQUEST										("IR", "Invalid Request"),
	USER_EXISTS											("UE", "User Already Exists"),
	USER_NOT_FOUND										("UNF", "User not found for specified Email"),
	BANK_NOT_FOUND										("BNF", "Bank Not Found"),
	CACHE_NOT_FOUND										("CNF", "Cache not found"),
	USER_TRANSACTIONS_NOT_FOUND							("UTNF", "User Transactions Not Found"),
	INTERNAL_SERVER_ERROR								("ISE", "Internal Server Error. Contact Support team for resolution"),
	GMAIL_EMAIL_NOT_FOUND								("ENF", "User Email Not found from gmail"),
	USER_PROPERTIES_NOT_FOUND							("UPNF", "User Properties Not Found"),
	TRANSACTION_NOT_FOUND								("TNF", "Transactions Not Found");

	private String code;
	private String message;

	private ApiResponseCodes(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
