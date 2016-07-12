package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Bank;
import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;
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

	public List<SearchQuery> getSearchQueryForBank(Long id) {
		return bankDao.getSearchQueryForBank(id);
	}

	public Parser getParserForSearchQuery(Long id) {
		return bankDao.getParserForSearchQuery(id);
	}

	public List<Bank> getAllEnabledBanks() {
		return bankDao.getAllEnabledBanks();
	}

}
