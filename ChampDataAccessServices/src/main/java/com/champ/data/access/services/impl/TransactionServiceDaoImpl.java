package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserTransaction;
import com.champ.data.access.services.IEntityDao;
import com.champ.data.access.services.ITransactionServiceDao;

@Service("transactionServiceDao")
public class TransactionServiceDaoImpl implements ITransactionServiceDao {

	@Autowired
	IEntityDao entityDao;

	private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceDaoImpl.class);

	@Transactional(value = TxType.REQUIRES_NEW)
	public AppUserTransaction saveUserTransaction(AppUserTransaction transaction) {
		try {
			return entityDao.saveOrUpdate(transaction);
		} catch (Exception e) {
			LOG.error("Failed to save transaction for user {}. Duplicate record found", transaction.getUser().getEmail());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AppUserTransaction> getUserTransactions(String email) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select transaction from AppUserTransaction transaction left join fetch transaction.user user where user.email =:email");
		query.setParameter("email", email);
		return (List<AppUserTransaction>) query.getResultList();
	}

}
