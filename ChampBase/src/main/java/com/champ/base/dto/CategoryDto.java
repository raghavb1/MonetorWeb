package com.champ.base.dto;

import java.io.Serializable;

public class CategoryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2172346914178483861L;
	private String category;
	private String iconUrl;
	private String color;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "CategoryDto [category=" + category + ", iconUrl=" + iconUrl + ", color=" + color + "]";
	}

}
