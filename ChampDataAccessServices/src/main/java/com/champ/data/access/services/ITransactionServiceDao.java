package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.AppUserTransaction;

public interface ITransactionServiceDao {

	public AppUserTransaction saveUserTransaction(AppUserTransaction transaction);

	public List<AppUserTransaction> getUserTransactions(String email);
}
