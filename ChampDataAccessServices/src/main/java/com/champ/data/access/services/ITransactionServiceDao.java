package com.champ.data.access.services;

import java.util.Date;
import java.util.List;

import com.champ.core.entity.AppUserTransaction;

public interface ITransactionServiceDao {

	public AppUserTransaction saveUserTransaction(AppUserTransaction transaction);

	public List<AppUserTransaction> getUserTransactions(String email);

	public boolean checkUserTransaction(Double amount, Date transactionDate, String submerchantCode,
			String email);
}
