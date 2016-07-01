package com.champ.services;

import java.util.List;

import com.champ.base.response.UserBank;
import com.champ.base.response.UserTransaction;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.gmail.api.response.GmailTokensResponse;

public interface IConverterService {

	public AppUser getUserFromRequest(GmailTokensResponse request);
	
	public List<UserBank> getUserBankFromBanks(List<Bank> banks);
	
	public List<UserTransaction> getUserTransactions(List<AppUserTransaction> transactions);
}
