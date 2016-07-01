package com.champ.services;

import java.util.List;

import com.champ.core.entity.AppUserTransaction;

public interface IAppUserTransactionService {

	public List<AppUserTransaction> getUserTransactions(String email, String token);
}
