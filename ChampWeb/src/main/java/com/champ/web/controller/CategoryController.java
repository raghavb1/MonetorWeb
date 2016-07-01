package com.champ.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.champ.core.entity.Category;
import com.champ.services.ICategoryService;

@Controller
@RequestMapping("/Category")
public class CategoryController {

	@Autowired
	ICategoryService categoryService;

	@RequestMapping("/create")
	public String addCategory(ModelMap map) {
		map.put("category", new Category());
		return "Category/create";
	}

	@RequestMapping("/save")
	public String saveCategory(@ModelAttribute("category") Category category, ModelMap map) {
		if (category.getId() != null) {
			Category persistedCategory = categoryService.findCategoryById(category.getId());
			persistedCategory.setCategoryImageUrl(category.getCategoryImageUrl());
			persistedCategory.setName(category.getName());
			categoryService.saveOrUpdateCategory(persistedCategory);
		}else{
			categoryService.saveOrUpdateCategory(category);	
		}
		map.put("message", "Category saved Successfully");
		List<Category> categories = categoryService.getAllCategories();
		map.put("categories", categories);
		return "Category/view";
	}

	@RequestMapping("/view")
	public String viewAllCategories(ModelMap map) {
		List<Category> categories = categoryService.getAllCategories();
		map.put("categories", categories);
		return "Category/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editCategory(@PathVariable("id") Long id, ModelMap map) {
		Category category = categoryService.findCategoryById(id);
		map.put("category", category);
		map.put("edit", "true");
		return "Category/create";
	}

	@RequestMapping("/checkCategory")
	public @ResponseBody String checkCategory(@ModelAttribute("name") String name) {
		boolean result = categoryService.checkCategory(name);
		if (result) {
			return "success";
		} else {
			return "failure";
		}
	}
}
