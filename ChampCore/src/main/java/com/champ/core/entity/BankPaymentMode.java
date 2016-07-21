package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_mode_id", referencedColumnName = "id")
	private PaymentMode paymentMode;

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

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((extractedString == null) ? 0 : extractedString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankPaymentMode other = (BankPaymentMode) obj;
		if (extractedString == null) {
			if (other.extractedString != null)
				return false;
		} else if (!extractedString.equals(other.extractedString))
			return false;
		return true;
	}

}
