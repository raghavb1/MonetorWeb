package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserLinkedAccount;
import com.champ.data.access.services.IAppUserLinkedAccountDao;
import com.champ.data.access.services.IEntityDao;

@Service("appUserLinkedAccountDao")
public class AppUserLinkedAccountDaoImpl implements IAppUserLinkedAccountDao {

	@Autowired
	IEntityDao entityDao;

	@SuppressWarnings("unchecked")
	public AppUserLinkedAccount getLinkedAccountByEmail(String email) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select linkedAccount from AppUserLinkedAccount linkedAccount where linkedAccount.email =:email");
		query.setParameter("email", email);
		List<AppUserLinkedAccount> linkedAccounts = (List<AppUserLinkedAccount>) query.getResultList();
		if (linkedAccounts.size() > 0) {
			return linkedAccounts.get(0);
		} else {
			return null;
		}
	}

	public AppUserLinkedAccount saveOrUpdateLinkedAccount(AppUserLinkedAccount linkedAccount) {
		return entityDao.saveOrUpdate(linkedAccount);
	}

	@SuppressWarnings("unchecked")
	public List<AppUserLinkedAccount> getLinkedAccountsForUser(String mobile) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select linkedAccount from AppUserLinkedAccount linkedAccount left join fetch linkedAccount.user user where user.mobile =:mobile");
		query.setParameter("mobile", mobile);
		List<AppUserLinkedAccount> linkedAccounts = (List<AppUserLinkedAccount>) query.getResultList();
		return linkedAccounts;
	}

	public List<AppUserLinkedAccount> getAllLinkedAccounts() {
		return entityDao.findAll(AppUserLinkedAccount.class);
	}

}
