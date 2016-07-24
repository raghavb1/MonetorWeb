package com.champ.base.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMappedTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1565209850226936704L;

	private Double amount;
	private String category;
	private String submerchantCode;
	private Long transactionId;
	private Date transactionDate;
	private String paymentMode;
	private String categoryColor;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubmerchantCode() {
		return submerchantCode;
	}

	public void setSubmerchantCode(String submerchantCode) {
		this.submerchantCode = submerchantCode;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getCategoryColor() {
		return categoryColor;
	}

	public void setCategoryColor(String categoryColor) {
		this.categoryColor = categoryColor;
	}

	@Override
	public String toString() {
		return "SaveTransactionRequest [amount=" + amount + ", category=" + category + ", submerchantName="
				+ submerchantCode + ", transactionId=" + transactionId + ", transactionDate=" + transactionDate
				+ ", paymentMode=" + paymentMode + "]";
	}

}
