package com.champ.services;

import java.util.List;

import com.champ.core.entity.AppUserBank;
import com.champ.core.entity.Bank;

public interface IAppUserBankService {

	public List<Bank> getBanksForUser(String mobile, String token);
	
	public AppUserBank saveUserBank(AppUserBank appUserBank);
}
