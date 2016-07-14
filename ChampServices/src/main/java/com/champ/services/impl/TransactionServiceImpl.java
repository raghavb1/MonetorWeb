package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.data.access.services.ITransactionServiceDao;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.services.IConverterService;
import com.champ.services.ITransactionService;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	ITransactionServiceDao transactionServiceDao;

	@Autowired
	IConverterService converterService;

	private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

	public void saveUserTransactions(List<TransactionDTO> transactions, AppUser user, Bank bank) {
		if (transactions != null && transactions.size() > 0) {
			LOG.info("{} transactions found for user {}", transactions.size(), user.getEmail());
			for (TransactionDTO dto : transactions) {
				AppUserTransaction transaction = converterService.getTransactionFromDto(dto, user, bank);
				if (transaction != null) {
					transactionServiceDao.saveUserTransaction(transaction);
				} else {
					LOG.info("Payment mode not found for string {} and bank {}", dto.getPaymentModeString(),
							bank.getName());
				}
			}
		}
	}

	public List<AppUserTransaction> getUserTransactions(String email) {
		return transactionServiceDao.getUserTransactions(email);
	}

}