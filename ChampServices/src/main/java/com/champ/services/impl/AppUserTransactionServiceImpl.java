package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserTransaction;
import com.champ.data.access.services.IAppUserTransactionDao;
import com.champ.services.IAppUserTransactionService;

@Service("appUserTransactionService")
@Transactional
public class AppUserTransactionServiceImpl implements IAppUserTransactionService {

	@Autowired
	IAppUserTransactionDao appUserTransactionDao;

	public List<AppUserTransaction> getUserTransactions(String email, String token) {
		return appUserTransactionDao.getUserTransactions(email, token);
	}

}
