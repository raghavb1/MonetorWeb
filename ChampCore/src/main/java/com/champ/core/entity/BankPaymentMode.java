package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bank_payment_mode")
public class BankPaymentMode extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -250165503861979558L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bank_id", referencedColumnName = "id")
	private Bank bank;

	@Column(name = "extracted_string")
	private String extractedString;

	@Column(name = "payment_mode")
	private String paymentMode;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getExtractedString() {
		return extractedString;
	}

	public void setExtractedString(String extractedString) {
		this.extractedString = extractedString;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

}
