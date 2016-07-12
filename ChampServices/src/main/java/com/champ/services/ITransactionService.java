package com.champ.services;

import java.util.List;

import com.champ.core.entity.AppUserTransaction;
import com.champ.gmail.api.dto.TransactionDTO;

public interface ITransactionService {

	public void saveUserTransactions(List<TransactionDTO> transactions);

	public List<AppUserTransaction> getUserTransactions(String email);

}
