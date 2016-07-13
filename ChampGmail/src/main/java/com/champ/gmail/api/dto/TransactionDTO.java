package com.champ.gmail.api.dto;

import java.util.Date;

public class TransactionDTO {

	private Double amount;
	private String subMerchant;
	private String paymentModeString;
	private Double balance;
	private Date date;
	private String transactionCode;

	public String getSubMerchant() {
		return subMerchant;
	}

	public void setSubMerchant(String subMerchant) {
		this.subMerchant = subMerchant;
	}

	public String getPaymentModeString() {
		return paymentModeString;
	}

	public void setPaymentModeString(String paymentModeString) {
		this.paymentModeString = paymentModeString;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	@Override
	public String toString() {
		return "TransactionDTO [amount=" + amount + ", subMerchant=" + subMerchant + ", transactionType="
				+ paymentModeString + ", balance=" + balance + ", date=" + date + ", transactionCode=" + transactionCode
				+ "]";
	}

}
