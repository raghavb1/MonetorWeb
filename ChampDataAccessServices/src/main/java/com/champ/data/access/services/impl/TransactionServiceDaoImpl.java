package com.champ.data.access.services.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserTransaction;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.ITransactionServiceDao;

@Service("transactionServiceDao")
public class TransactionServiceDaoImpl implements ITransactionServiceDao {

	@Autowired
	IEntityDao entityDao;

	public AppUserTransaction saveUserTransaction(AppUserTransaction transaction) {
		return entityDao.saveOrUpdate(transaction);
	}

	@SuppressWarnings("unchecked")
	public List<AppUserTransaction> getUserTransactions(String email) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select transaction from AppUserTransaction transaction left join fetch transaction.user user where user.email =:email");
		query.setParameter("email", email);
		return (List<AppUserTransaction>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public boolean checkUserTransaction(Double amount, Date transactionDate, String submerchantCode, String email) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select transaction from AppUserTransaction transaction left join fetch transaction.user user left join fetch transaction.subMerchant submerchant where transaction.amount =:amount and transaction.transactionDate =:date and user.email =:email and submerchant.code =:code");
		query.setParameter("amount", amount);
		query.setParameter("date", transactionDate);
		query.setParameter("code", submerchantCode);
		query.setParameter("email", email);
		List<AppUserTransaction> transactions = query.getResultList();
		if (transactions != null && transactions.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

}
