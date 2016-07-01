package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.SubMerchant;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.ISubMerchantDao;

@Service("subMerchantDao")
public class SubMerchantDaoImpl implements ISubMerchantDao {

	@Autowired
	IEntityDao entityDao;

	public List<SubMerchant> getAllUnaprovedSubMerchants() {
		return entityDao.findAllUnapprovedObjects(SubMerchant.class);
	}

	public void approveSubmerchant(SubMerchant subMerchant) {
		subMerchant.setApproved(true);
		entityDao.saveOrUpdate(subMerchant);
	}

	public SubMerchant getSubMerchantById(Long id) {
		return entityDao.findById(SubMerchant.class, id);
	}

	public void saveOrUpdateSubMerchant(SubMerchant subMerchant) {
		entityDao.saveOrUpdate(subMerchant);
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

}
