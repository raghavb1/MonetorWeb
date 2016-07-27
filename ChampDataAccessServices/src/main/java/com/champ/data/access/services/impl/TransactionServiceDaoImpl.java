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

	@SuppressWarnings("unchecked")
	public List<AppUserTransaction> getUserTransactions(Long id) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select transaction from AppUserTransaction transaction left join fetch transaction.subMerchant subMerchant "
						+ "left join fetch transaction.bank bank left join fetch transaction.paymentMode paymentMode "
						+ "left join fetch transaction.category category "
						+ "left join fetch transaction.user user where user.id =:id and transaction.userDefined = false");
		query.setParameter("id", id);
		return (List<AppUserTransaction>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public AppUserTransaction getTransactionByUserId(Long userId, Long id) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select transaction from AppUserTransaction transaction left join fetch transaction.user user where user.id =:userId and transaction.id =:id");
		query.setParameter("userId", userId);
		query.setParameter("id", id);
		List<AppUserTransaction> transactions = (List<AppUserTransaction>) query.getResultList();
		if (transactions != null && transactions.size() > 0) {
			return transactions.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AppUserTransaction> getUserCreatedTransactions(Long id) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select transaction from AppUserTransaction transaction left join fetch transaction.subMerchant subMerchant "
						+ "left join fetch transaction.bank bank left join fetch transaction.paymentMode paymentMode "
						+ "left join fetch transaction.category category "
						+ "left join fetch transaction.user user where user.id =:id and transaction.userDefined = true order by transaction.transactionDate DESC");
		query.setParameter("id", id);
		return (List<AppUserTransaction>) query.getResultList();
	}
}
