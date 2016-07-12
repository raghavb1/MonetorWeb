package com.champ.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.dto.SearchQueryParserDto;
import com.champ.core.entity.Bank;

@Cache(name = "bankCache")
public class BankCache {

	private List<Bank> banks = new ArrayList<Bank>();
	private Map<String, List<SearchQueryParserDto>> bankNameToSearchQuery = new HashMap<String, List<SearchQueryParserDto>>();

	public void addBank(Bank bank, SearchQueryParserDto dto) {
		banks.add(bank);
		if (bankNameToSearchQuery.get(bank.getName()) == null) {
			bankNameToSearchQuery.put(bank.getName(), new ArrayList<SearchQueryParserDto>());
		}
		bankNameToSearchQuery.get(bank.getName()).add(dto);
	}

	public List<SearchQueryParserDto> getSearchQueryParserByBankName(String bankName) {
		return bankNameToSearchQuery.get(bankName);
	}

	public List<Bank> getAllBanks() {
		return banks;
	}
}
