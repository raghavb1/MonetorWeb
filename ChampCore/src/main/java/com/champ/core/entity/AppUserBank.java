package com.champ.core.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app_user_bank")
public class AppUserBank extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1843086517811053908L;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private AppUser user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bank_id", referencedColumnName = "id")
	private Bank bank;

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

}
