package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.champ.core.entity.BaseEntity;
import com.champ.data.access.services.IEntityDao;

@Repository
public class EntityDaoImpl implements IEntityDao {

	@PersistenceContext
	EntityManager entityManager;

	public <T extends BaseEntity> T saveOrUpdate(T object) {
		if (object.getId() != null) {
			object = entityManager.merge(object);
		} else {
			entityManager.persist(object);
		}
		return object;
	}

	public <T extends BaseEntity> void delete(T object) {
		entityManager.remove(object);
	}

	public <T extends BaseEntity> T findById(Class<T> objectClass, Long id) {
		T object = null;
		object = entityManager.find(objectClass, id);
		return object;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findAll(Class<T> objectClass) {
		Query query = entityManager.createQuery("Select data from " + objectClass.getName() + " data");
		List<T> objectList = (List<T>) query.getResultList();
		return objectList;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findAllApprovedObjects(Class<T> objectClass) {
		Query query = entityManager
				.createQuery("Select data from " + objectClass.getName() + " data where data.approved = true");
		List<T> objectList = (List<T>) query.getResultList();
		return objectList;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findAllUnapprovedObjects(Class<T> objectClass) {
		Query query = entityManager
				.createQuery("Select data from " + objectClass.getName() + " data where data.approved = false");
		List<T> objectList = (List<T>) query.getResultList();
		return objectList;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findAllEnabledObjects(Class<T> objectClass) {
		Query query = entityManager
				.createQuery("Select data from " + objectClass.getName() + " data where data.enabled = true");
		List<T> objectList = (List<T>) query.getResultList();
		return objectList;
	}

}
