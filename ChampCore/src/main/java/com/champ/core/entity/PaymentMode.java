package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "payment_mode")
public class PaymentMode extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8236366686840435613L;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "icon_url")
	private String iconUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Override
	public String toString() {
		return "PaymentMode [name=" + name + ", iconUrl=" + iconUrl + "]";
	}

}
