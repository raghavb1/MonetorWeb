package com.champ.base.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.champ.base.dto.CategoryDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1945508065892082723L;

	private List<CategoryDto> categories;

	public List<CategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDto> categories) {
		this.categories = categories;
	}

}
