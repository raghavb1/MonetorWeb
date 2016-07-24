package com.champ.data.access.services;

import java.util.Date;
import java.util.List;

import com.champ.core.entity.AppUserTransaction;

public interface ITransactionServiceDao {

	public AppUserTransaction saveUserTransaction(AppUserTransaction transaction);

	public boolean checkUserTransaction(Double amount, Date transactionDate, String submerchantCode, String email);

	public List<AppUserTransaction> getUserTransactions(String email, String token);

	public AppUserTransaction getUserTransactionByIdAndEmail(String email, String token, Long id);
	
	public List<AppUserTransaction> getUserCreatedTransactions(String email, String token);
}
