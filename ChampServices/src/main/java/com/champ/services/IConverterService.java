package com.champ.services;

import java.util.List;

import com.champ.base.response.UserBank;
import com.champ.base.response.UserTransaction;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;

public interface IConverterService {

	public AppUser getUserFromRequest(GmailTokensResponse request, UserInfoResponse userInfo, AppUser user);

	public List<UserBank> getUserBankFromBanks(List<Bank> banks);

	public List<UserTransaction> getUserTransactions(List<AppUserTransaction> transactions);

	public String generateTokenForUser();

	public AppUserTransaction getTransactionFromDto(TransactionDTO dto, AppUser user, Bank bank);
}
