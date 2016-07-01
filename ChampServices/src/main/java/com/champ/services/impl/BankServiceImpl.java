package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Bank;
import com.champ.data.access.services.IBankDao;
import com.champ.services.IBankService;

@Service("bankService")
@Transactional
public class BankServiceImpl implements IBankService {

	@Autowired
	IBankDao bankDao;

	public List<Bank> getAllBanks() {
		return bankDao.getAllBanks();
	}

	public void saveOrUpdateBank(Bank bank) {
		bankDao.saveOrUpdateBank(bank);
	}

	public Bank findBankById(Long id) {
		return bankDao.findBankById(id);
	}

	public void enableBank(Long id) {
		bankDao.enableBank(id);
	}

	public void disableBank(Long id) {
		bankDao.disableBank(id);
	}

	public boolean checkBank(String name) {
		return bankDao.checkBank(name);
	}

}
