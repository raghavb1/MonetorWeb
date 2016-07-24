package com.champ.base.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FailedTransactionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4656331971939957028L;

	private String submerchantCode;
	private Double amount;
	private String reason;
	private Long transactionId;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getSubmerchantCode() {
		return submerchantCode;
	}

	public void setSubmerchantCode(String submerchantCode) {
		this.submerchantCode = submerchantCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public FailedTransactionDto(String submerchantCode, Double amount, String reason, Long transactionId) {
		super();
		this.submerchantCode = submerchantCode;
		this.amount = amount;
		this.reason = reason;
		this.transactionId = transactionId;
	}

	public FailedTransactionDto() {
		super();
	}

	@Override
	public String toString() {
		return "FailedTransactionDto [submerchantCode=" + submerchantCode + ", amount=" + amount + ", reason=" + reason
				+ ", transactionId=" + transactionId + "]";
	}

}
