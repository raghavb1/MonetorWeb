package com.champ.core.dto;

import java.io.Serializable;

public class BulkUploadError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 679477457509783173L;

	private String field;
	private String reason;

	public BulkUploadError() {
		super();
	}

	public BulkUploadError(String field, String reason) {
		super();
		this.field = field;
		this.reason = reason;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
