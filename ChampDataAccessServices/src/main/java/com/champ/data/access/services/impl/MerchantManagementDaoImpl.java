package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Merchant;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.IMerchantManagementDao;

@Service("mechantManagementDao")
public class MerchantManagementDaoImpl implements IMerchantManagementDao {

	@Autowired
	IEntityDao entityDao;

	public List<Merchant> getAllMerchants() {
		return entityDao.findAll(Merchant.class);
	}

	public void saveOrUpdateMerchant(Merchant merchant) {
		entityDao.saveOrUpdate(merchant);
	}

	public Merchant findMerchantById(Long id) {
		return entityDao.findById(Merchant.class, id);
	}

	public void enableMerchant(Long id) {
		Merchant merchant = entityDao.findById(Merchant.class, id);
		if (merchant != null) {
			merchant.setEnabled(true);
			entityDao.saveOrUpdate(merchant);
		}
	}

	public void disableMerchant(Long id) {
		Merchant merchant = entityDao.findById(Merchant.class, id);
		if (merchant != null) {
			merchant.setEnabled(false);
			entityDao.saveOrUpdate(merchant);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean checkMerchant(String name) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select merchant.name from Merchant merchant where merchant.name = :name");
		query.setParameter("name", name);
		List<String> names = (List<String>) query.getResultList();
		if (names.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
