package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Category;
import com.champ.data.access.services.ICategoryDao;
import com.champ.data.access.services.IEntityDao;

@Service("categoryDao")
public class CategoryDaoImpl implements ICategoryDao {

	@Autowired
	IEntityDao entityDao;

	public List<Category> getAllCategories() {
		return entityDao.findAll(Category.class);
	}

	public void saveOrUpdateCategory(Category category) {
		entityDao.saveOrUpdate(category);
	}

	public Category findCategoryById(Long id) {
		return entityDao.findById(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	public boolean checkCategory(String name) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select category.name from Category category where category.name = :name");
		query.setParameter("name", name);
		List<String> names = (List<String>) query.getResultList();
		if (names.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

}
