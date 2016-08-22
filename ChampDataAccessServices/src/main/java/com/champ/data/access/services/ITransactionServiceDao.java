package com.champ.data.access.services;

import java.util.Date;
import java.util.List;

import com.champ.core.entity.AppUserTransaction;

public interface ITransactionServiceDao {

	public AppUserTransaction saveUserTransaction(AppUserTransaction transaction);

	public boolean checkUserTransaction(Double amount, Date transactionDate, String submerchantCode, String mobile);

	public List<AppUserTransaction> getUserTransactions(Long id);

	public AppUserTransaction getTransactionByUserId(Long userId, Long id);
	
	public List<AppUserTransaction> getUserCreatedTransactions(Long id);
}
