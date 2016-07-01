package com.champ.core.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.entity.Bank;

@Cache(name = "appUserBankCache")
public class AppUserBankCache {

	private Map<String, List<Bank>> appUserBanks = new HashMap<String, List<Bank>>();

	private static final String separator = "~";

	public void addAppUserBank(String email, String token, List<Bank> banks) {
		appUserBanks.put(email + separator + token, banks);
	}

	public List<Bank> getBanksForUser(String email, String token) {
		return appUserBanks.get(email + separator + token);
	}
}
