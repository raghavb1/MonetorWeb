package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Category;
import com.champ.data.access.services.ICategoryDao;
import com.champ.services.ICategoryService;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	ICategoryDao categoryDao;

	public List<Category> getAllCategories() {
		return categoryDao.getAllCategories();
	}

	public Category saveOrUpdateCategory(Category category) {
		return categoryDao.saveOrUpdateCategory(category);
	}

	public Category findCategoryById(Long id) {
		return categoryDao.findCategoryById(id);
	}

	public boolean checkCategory(String name) {
		return categoryDao.checkCategory(name);
	}

	public Category findCategoryByName(String name) {
		return categoryDao.findCategoryByName(name);
	}

}
