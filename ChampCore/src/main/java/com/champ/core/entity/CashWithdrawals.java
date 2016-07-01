package com.champ.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cash_withdrawals")
public class CashWithdrawals extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7126152507073907117L;

	@ManyToOne
	@JoinColumn(name = "transaction_id", referencedColumnName = "id")
	private AppUserTransaction appUserTransaction;

	@ManyToOne
	@JoinColumn(name = "category", referencedColumnName = "id")
	private Category category;

	@Column(name = "amount")
	private BigDecimal amount;

	public AppUserTransaction getAppUserTransaction() {
		return appUserTransaction;
	}

	public void setAppUserTransaction(AppUserTransaction appUserTransaction) {
		this.appUserTransaction = appUserTransaction;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
