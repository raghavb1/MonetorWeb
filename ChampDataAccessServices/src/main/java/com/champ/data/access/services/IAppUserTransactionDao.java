package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.AppUserTransaction;

public interface IAppUserTransactionDao {

	public List<AppUserTransaction> getUserTransactions(String email, String token);
}
