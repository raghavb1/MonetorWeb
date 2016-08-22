package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.AppUserLinkedAccount;

public interface IAppUserLinkedAccountDao {

	public AppUserLinkedAccount getLinkedAccountByEmail(String email);
	
	public AppUserLinkedAccount saveOrUpdateLinkedAccount(AppUserLinkedAccount linkedAccount);
	
	public List<AppUserLinkedAccount> getLinkedAccountsForUser(String mobile);
	
	public List<AppUserLinkedAccount> getAllLinkedAccounts();
}
