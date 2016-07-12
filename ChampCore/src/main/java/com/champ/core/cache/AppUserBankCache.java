package com.champ.core.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.Bank;

@Cache(name = "appUserBankCache")
public class AppUserBankCache {

	private Map<String, List<Bank>> appUserBanks = new HashMap<String, List<Bank>>();
	private Map<String, List<Bank>> emailToBanks = new HashMap<String, List<Bank>>();

	private static final String separator = "~";

	public void addAppUserBank(String email, String token, List<Bank> banks) {
		emailToBanks.put(email, banks);
		appUserBanks.put(email + separator + token, banks);
	}

	public List<Bank> getBanksForUserByEmailAndToken(String email, String token) {
		return appUserBanks.get(email + separator + token);
	}

	public List<Bank> getBanksForUserByEmail(String email) {
		return emailToBanks.get(email);
	}

	public void updateBankToUser(AppUser user, Bank bank) {
		emailToBanks.get(user.getEmail()).add(bank);
		appUserBanks.get(user.getEmail() + separator + user.getToken()).add(bank);
	}
}
