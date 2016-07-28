package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.Bank;
import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;

public interface IBankDao {

	public List<Bank> getAllBanks();

	public void saveOrUpdateBank(Bank bank);

	public Bank findBankById(Long id);

	public void enableBank(Long id);

	public void disableBank(Long id);

	public boolean checkBank(String name);

	public List<SearchQuery> getSearchQueryForBank(Long id);

	public List<Parser> getParserForSearchQuery(Long id);
	
	public List<Bank> getAllEnabledBanks();
}
