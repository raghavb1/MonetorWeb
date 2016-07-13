package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.BankPaymentMode;
import com.champ.data.access.services.IBankPaymentDao;
import com.champ.data.access.services.IEntityDao;

@Service("bankPaymentDao")
public class BankPaymentDaoImpl implements IBankPaymentDao {

	@Autowired
	IEntityDao entityDao;

	public List<BankPaymentMode> getAllBankPaymentModes() {
		return entityDao.findAll(BankPaymentMode.class);
	}

	public void saveOrUpdateBankPaymentMode(BankPaymentMode bankPaymentMode) {
		entityDao.saveOrUpdate(bankPaymentMode);
	}

	public BankPaymentMode findBankPaymentModeById(Long id) {
		return entityDao.findById(BankPaymentMode.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<BankPaymentMode> findBankPaymentModesByBankId(Long id) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select mode from BankPaymentMode mode left join fetch mode.bank bank where bank.id =:id");
		query.setParameter("id", id);
		return (List<BankPaymentMode>) query.getResultList();
	}

}
