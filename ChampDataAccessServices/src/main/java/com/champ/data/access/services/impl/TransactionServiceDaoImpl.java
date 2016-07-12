package com.champ.data.access.services.impl;

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

}
