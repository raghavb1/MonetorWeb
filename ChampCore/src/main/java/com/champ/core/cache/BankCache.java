package com.champ.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.dto.SearchQueryParserDto;
import com.champ.core.entity.Bank;
import com.champ.core.entity.BankPaymentMode;

@Cache(name = "bankCache")
public class BankCache {

	private List<Bank> banks = new ArrayList<Bank>();
	private Map<String, List<SearchQueryParserDto>> bankNameToSearchQuery = new HashMap<String, List<SearchQueryParserDto>>();
	private Map<String, Map<String, BankPaymentMode>> bankNameToPaymentModes = new HashMap<String, Map<String, BankPaymentMode>>();

	public void addBank(Bank bank, SearchQueryParserDto dto) {
		banks.add(bank);
		if (dto != null) {
			if (bankNameToSearchQuery.get(bank.getName()) == null) {
				bankNameToSearchQuery.put(bank.getName(), new ArrayList<SearchQueryParserDto>());
			}
			bankNameToSearchQuery.get(bank.getName()).add(dto);
		}
	}

	public void addBankPaymentModes(String bankName, BankPaymentMode paymentMode) {
		if (paymentMode != null) {
			if (bankNameToPaymentModes.get(bankName) == null) {
				bankNameToPaymentModes.put(bankName, new HashMap<String, BankPaymentMode>());
			}
			bankNameToPaymentModes.get(bankName).put(paymentMode.getExtractedString().toUpperCase(), paymentMode);
		}
	}

	public List<SearchQueryParserDto> getSearchQueryParserByBankName(String bankName) {
		return bankNameToSearchQuery.get(bankName);
	}

	public List<Bank> getAllBanks() {
		return banks;
	}

	public BankPaymentMode getPaymentModeByBankNameAndString(String bankName, String extractedString) {
		if (bankNameToPaymentModes.get(bankName) != null) {
			return bankNameToPaymentModes.get(bankName).get(extractedString.toUpperCase());
		}
		return null;
	}
}
