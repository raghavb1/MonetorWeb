package com.champ.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "app_user_transaction", uniqueConstraints = @UniqueConstraint(columnNames = { "amount",
		"transaction_date", "subMerchant_id", "user_id" }))
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
	@JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = true)
	private Bank bank;

	@Column(name = "payment_mode_string")
	private String paymentModeString;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private AppUser user;

	@Column(name = "user_defined")
	private Boolean userDefined = false;

	@ManyToOne
	@JoinTable(name = "transaction_category_override", joinColumns = @JoinColumn(name = "transaction_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Category category;

	@Version
	private Long version;

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

	public String getPaymentModeString() {
		return paymentModeString;
	}

	public void setPaymentModeString(String paymentModeString) {
		this.paymentModeString = paymentModeString;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Boolean getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(Boolean userDefined) {
		this.userDefined = userDefined;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
