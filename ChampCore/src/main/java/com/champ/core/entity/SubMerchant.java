package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sub_merchant")
public class SubMerchant extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5644259093264502006L;

	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "approved")
	private Boolean approved = false;

	@ManyToOne(optional = true)
	@JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = true)
	Merchant merchant;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

}
