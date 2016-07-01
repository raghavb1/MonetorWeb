package com.champ.services;

import java.util.List;

import com.champ.core.entity.Bank;

public interface IBankService {

	public List<Bank> getAllBanks();

	public void saveOrUpdateBank(Bank bank);

	public Bank findBankById(Long id);

	public void enableBank(Long id);

	public void disableBank(Long id);

	public boolean checkBank(String name);

}
