package com.champ.services;

import java.util.Date;
import java.util.List;

import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.gmail.api.dto.TransactionDTO;

public interface ITransactionService {

	public void saveUserTransactions(List<TransactionDTO> transactions, AppUser user, Bank bank);

	public boolean checkUserTransaction(Double amount, Date transactionDate, String submerchantCode, String mobile);

	public List<AppUserTransaction> getUserTransactions(Long id);

	public AppUserTransaction getTransactionByUserId(Long userId, Long id);

	public AppUserTransaction saveTransaction(AppUserTransaction transaction);

	public List<AppUserTransaction> getUserCreatedTransactions(Long id);

}
