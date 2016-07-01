package com.champ.data.access.services;

import java.util.List;

import javax.persistence.EntityManager;

import com.champ.core.entity.BaseEntity;

public interface IEntityDao {

	public <T extends BaseEntity> T saveOrUpdate(T object);

	public <T extends BaseEntity> void delete(T object);

	public <T extends BaseEntity> T findById(Class<T> objectClass, Long id);

	public <T extends BaseEntity> List<T> findAll(Class<T> objectClass);

	public <T extends BaseEntity> List<T> findAllApprovedObjects(Class<T> objectClass);
	
	public <T extends BaseEntity> List<T> findAllUnapprovedObjects(Class<T> objectClass);
	
	public <T extends BaseEntity> List<T> findAllEnabledObjects(Class<T> objectClass);

	public EntityManager getEntityManager();
}
