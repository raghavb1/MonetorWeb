package com.champ.gmail.api.dto;

import java.util.Date;

public class TransactionDTO {

	private Double amount;
	private String subMerchant;
	private String transactionType;
	private Double balance;
	private Date date;

	public String getSubMerchant() {
		return subMerchant;
	}

	public void setSubMerchant(String subMerchant) {
		this.subMerchant = subMerchant;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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

	@Override
	public String toString() {
		return "TransactionDTO [amount=" + amount + ", subMerchant=" + subMerchant + ", transactionType="
				+ transactionType + ", balance=" + balance + ", date=" + date + "]";
	}

}
