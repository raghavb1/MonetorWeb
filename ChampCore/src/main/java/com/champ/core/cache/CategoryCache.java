package com.champ.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.base.dto.CategoryDto;
import com.champ.core.annotation.Cache;
import com.champ.core.entity.Category;

@Cache(name = "categoryCache")
public class CategoryCache {

	private List<CategoryDto> categories = new ArrayList<CategoryDto>();
	private Map<String, Category> nameToCategory = new HashMap<String, Category>();

	public void addCategory(Category category) {
		CategoryDto dto = new CategoryDto();
		dto.setCategory(category.getName());
		dto.setColor(category.getColor());
		dto.setIconUrl(category.getCategoryImageUrl());
		categories.add(dto);
		nameToCategory.put(category.getName(), category);
	}

	public List<CategoryDto> getCategories() {
		return categories;
	}

	public Category getCategoryByName(String name) {
		return nameToCategory.get(name);
	}
}
