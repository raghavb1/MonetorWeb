package com.champ.base.dto;

import java.io.Serializable;

public class PaymentModeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3981895675195338651L;
	private String name;
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

}
