package com.champ.core.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_suggested_mapping")
public class UserSuggestedMerchant extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4853440291615731799L;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private AppUser user;

	@ManyToOne
	@JoinColumn(name = "sub_merchant_id", referencedColumnName = "id")
	private SubMerchant subMerchant;

	public SubMerchant getSubMerchant() {
		return subMerchant;
	}

	public void setSubMerchant(SubMerchant subMerchant) {
		this.subMerchant = subMerchant;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

}
