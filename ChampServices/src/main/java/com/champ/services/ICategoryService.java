package com.champ.services;

import java.util.List;

import com.champ.core.entity.Category;

public interface ICategoryService {

	public List<Category> getAllCategories();
	
	public void saveOrUpdateCategory(Category category);

	public Category findCategoryById(Long id);
	
	public boolean checkCategory(String name);
}
