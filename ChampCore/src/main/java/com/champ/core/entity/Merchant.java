package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "merchant")
public class Merchant extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3688065146276834301L;

	@Column(name = "name")
	private String name;

	@Column(name = "image_url", nullable = true)
	private String imageUrl;

	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;

	@Column(name = "enabled")
	private Boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
