package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Bank;
import com.champ.data.access.services.IBankDao;
import com.champ.data.access.services.IEntityDao;

@Service("bankDao")
public class BankDaoImpl implements IBankDao {

	@Autowired
	IEntityDao entityDao;

	public List<Bank> getAllBanks() {
		return entityDao.findAll(Bank.class);
	}

	public void saveOrUpdateBank(Bank bank) {
		entityDao.saveOrUpdate(bank);
	}

	public Bank findBankById(Long id) {
		return entityDao.findById(Bank.class, id);
	}

	public void enableBank(Long id) {
		Bank bank = entityDao.findById(Bank.class, id);
		bank.setEnabled(true);
		entityDao.saveOrUpdate(bank);
	}

	public void disableBank(Long id) {
		Bank bank = entityDao.findById(Bank.class, id);
		bank.setEnabled(false);
		entityDao.saveOrUpdate(bank);
	}

	@SuppressWarnings("unchecked")
	public boolean checkBank(String name) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select bank.name from Bank bank where bank.name = :name");
		query.setParameter("name", name);
		List<String> names = (List<String>) query.getResultList();
		if (names.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

}
