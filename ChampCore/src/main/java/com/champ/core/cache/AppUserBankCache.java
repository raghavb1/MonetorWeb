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

	public void addAppUserBank(String mobile, String token, List<Bank> banks) {
		emailToBanks.put(mobile, banks);
		appUserBanks.put(mobile + separator + token, banks);
	}

	public List<Bank> getBanksForUserByMobileAndToken(String mobile, String token) {
		return appUserBanks.get(mobile + separator + token);
	}

	public List<Bank> getBanksForUserByMobile(String mobile) {
		return emailToBanks.get(mobile);
	}

	public void updateBankToUser(AppUser user, Bank bank) {
		emailToBanks.get(user.getMobile()).add(bank);
		appUserBanks.get(user.getMobile() + separator + user.getToken()).add(bank);
	}
}
