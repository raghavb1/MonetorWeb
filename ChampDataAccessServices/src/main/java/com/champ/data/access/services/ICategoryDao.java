package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.Category;

public interface ICategoryDao {

	public List<Category> getAllCategories();
	
	public Category saveOrUpdateCategory(Category category);

	public Category findCategoryById(Long id);
	
	public boolean checkCategory(String name);
	
	public Category findCategoryByName(String name);
}
