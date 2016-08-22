package com.champ.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserLinkedAccount;
import com.champ.data.access.services.IAppUserLinkedAccountDao;
import com.champ.services.IAppUserLinkedAccountService;

@Service("appUserLinkedAccountService")
public class AppUserLinkedAccountServiceImpl implements IAppUserLinkedAccountService {

	@Autowired
	IAppUserLinkedAccountDao appUserLinkedAccountDao;

	public AppUserLinkedAccount getLinkedAccountByEmail(String email) {
		return appUserLinkedAccountDao.getLinkedAccountByEmail(email);
	}

	public AppUserLinkedAccount saveOrUpdateLinkedAccount(AppUserLinkedAccount linkedAccount) {
		return appUserLinkedAccountDao.saveOrUpdateLinkedAccount(linkedAccount);
	}

	public List<AppUserLinkedAccount> getLinkedAccountsForUser(String mobile) {
		return appUserLinkedAccountDao.getLinkedAccountsForUser(mobile);
	}

	public List<AppUserLinkedAccount> getAllLinkedAccounts() {
		return appUserLinkedAccountDao.getAllLinkedAccounts();
	}

}
