package com.champ.services;

import java.util.List;

import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.gmail.api.dto.TransactionDTO;

public interface ITransactionService {

	public void saveUserTransactions(List<TransactionDTO> transactions, AppUser user, Bank bank);

	public List<AppUserTransaction> getUserTransactions(String email);

}
