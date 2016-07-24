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

	@SuppressWarnings("unchecked")
	public List<Category> getAllCategories() {
		Query query = entityDao.getEntityManager()
				.createQuery("Select category from Category category where category.userDefined = false");
		return (List<Category>) query.getResultList();
	}

	public Category saveOrUpdateCategory(Category category) {
		return entityDao.saveOrUpdate(category);
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
		if (names != null && names.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public Category findCategoryByName(String name) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select category from Category category where category.name = :name");
		query.setParameter("name", name);
		List<Category> categories = (List<Category>) query.getResultList();
		if (categories != null && categories.size() > 0) {
			return categories.get(0);
		} else {
			return null;
		}
	}

}
