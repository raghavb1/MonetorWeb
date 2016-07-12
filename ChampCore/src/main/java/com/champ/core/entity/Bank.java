package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank")
public class Bank extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5435753640219968110L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "icon_url", nullable = true)
	private String iconUrl;

	@Column(name = "website_link", nullable = true)
	private String websiteLink;

	@Column(name = "enabled")
	private Boolean enabled = true;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

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

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}

}
