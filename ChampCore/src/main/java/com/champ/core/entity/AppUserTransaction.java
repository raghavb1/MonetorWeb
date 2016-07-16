package com.champ.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "app_user_transaction")
public class AppUserTransaction extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -568981348390424206L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date")
	private Date transactionDate;

	@ManyToOne
	private SubMerchant subMerchant;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "post_transaction_balance")
	private Double postTransactionBalance;

	@Column(name = "transaction_code")
	private String transactionCode;

	@ManyToOne
	@JoinColumn(name = "bank_id", referencedColumnName = "id")
	private Bank bank;

	@ManyToOne
	@JoinColumn(name = "payment_mode_id", referencedColumnName = "id", nullable = true)
	private BankPaymentMode bankPaymentMode;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private AppUser user;

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public SubMerchant getSubMerchant() {
		return subMerchant;
	}

	public void setSubMerchant(SubMerchant subMerchant) {
		this.subMerchant = subMerchant;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPostTransactionBalance() {
		return postTransactionBalance;
	}

	public void setPostTransactionBalance(Double postTransactionBalance) {
		this.postTransactionBalance = postTransactionBalance;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankPaymentMode getBankPaymentMode() {
		return bankPaymentMode;
	}

	public void setBankPaymentMode(BankPaymentMode bankPaymentMode) {
		this.bankPaymentMode = bankPaymentMode;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

}
