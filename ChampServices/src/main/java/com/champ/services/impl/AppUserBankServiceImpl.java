package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserBank;
import com.champ.core.entity.Bank;
import com.champ.data.access.services.IAppUserBankDao;
import com.champ.services.IAppUserBankService;

@Service("appUserBankService")
@Transactional 
public class AppUserBankServiceImpl implements IAppUserBankService {

	@Autowired
	IAppUserBankDao appUserBankDao;

	public List<Bank> getBanksForUser(String email, String token) {
		return appUserBankDao.getBanksForUser(email, token);
	}

	public AppUserBank saveUserBank(AppUserBank appUserBank) {
		return appUserBankDao.saveUserBank(appUserBank);
	}

}
