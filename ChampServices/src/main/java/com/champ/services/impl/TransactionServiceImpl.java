package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserTransaction;
import com.champ.data.access.services.ITransactionServiceDao;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.services.ITransactionService;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements ITransactionService{

	@Autowired
	ITransactionServiceDao	transactionServiceDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);
	
	public void saveUserTransactions(List<TransactionDTO> transactions) {
		LOG.info("Code to convert dto into entity here");
	}

	public List<AppUserTransaction> getUserTransactions(String email) {
		return transactionServiceDao.getUserTransactions(email);
	}

}
