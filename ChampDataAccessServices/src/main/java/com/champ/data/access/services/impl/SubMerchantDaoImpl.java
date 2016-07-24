package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.cache.PropertyMapCache;
import com.champ.core.entity.SubMerchant;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.ISubMerchantDao;

@Service("subMerchantDao")
public class SubMerchantDaoImpl implements ISubMerchantDao {

	@Autowired
	IEntityDao entityDao;

	@SuppressWarnings("unchecked")
	public List<SubMerchant> getAllUnaprovedSubMerchants() {
		Query query = entityDao.getEntityManager().createQuery("Select submerchant from SubMerchant submerchant where submerchant.approved = false and submerchant.userDefined = false order by submerchant.created DESC");
		query.setMaxResults(CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyInteger(Property.SUBMERCHANT_FETCH_LIMIT));
		return (List<SubMerchant>)query.getResultList();
	}

	public void approveSubmerchant(SubMerchant subMerchant) {
		subMerchant.setApproved(true);
		entityDao.saveOrUpdate(subMerchant);
	}

	public SubMerchant getSubMerchantById(Long id) {
		return entityDao.findById(SubMerchant.class, id);
	}

	public SubMerchant saveOrUpdateSubMerchant(SubMerchant subMerchant) {
		return entityDao.saveOrUpdate(subMerchant);
	}

	@SuppressWarnings("unchecked")
	public boolean checkSubMerchant(String code) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select submerchant.code from SubMerchant submerchant where submerchant.code = :code");
		query.setParameter("code", code);
		List<String> codes = (List<String>) query.getResultList();
		if (codes.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public List<SubMerchant> getAllSubMerchants() {
		return entityDao.findAll(SubMerchant.class);
	}

	@SuppressWarnings("unchecked")
	public SubMerchant findSubMerchantByCode(String code) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select submerchant from SubMerchant submerchant where submerchant.code = :code");
		query.setParameter("code", code);
		List<SubMerchant> codes = (List<SubMerchant>) query.getResultList();
		if (codes.size() > 0) {
			return codes.get(0);
		} else {
			return null;
		}
	}

}
