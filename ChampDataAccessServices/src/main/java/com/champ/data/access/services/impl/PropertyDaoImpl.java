package com.champ.data.access.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Property;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.IPropertyDao;

@Service("propertyDao")
@Transactional
public class PropertyDaoImpl implements IPropertyDao {

	@Autowired
	IEntityDao entityDao;

	public List<Property> getAllProperties() {
		return entityDao.findAll(Property.class);
	}

}
