package com.champ.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Category extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1446101460363751086L;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "category_image_url", nullable = true)
	private String categoryImageUrl;

	@Column(name = "color")
	private String color;

	@Column(name = "user_defined")
	private Boolean userDefined = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryImageUrl() {
		return categoryImageUrl;
	}

	public void setCategoryImageUrl(String categoryImageUrl) {
		this.categoryImageUrl = categoryImageUrl;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(Boolean userDefined) {
		this.userDefined = userDefined;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + ", categoryImageUrl=" + categoryImageUrl + ", color=" + color
				+ ", userDefined=" + userDefined + "]";
	}

}
