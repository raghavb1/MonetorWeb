package com.champ.base.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2828267386239891467L;

	private String from;
	private String message;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MessageDto [from=" + from + ", message=" + message + "]";
	}
}
