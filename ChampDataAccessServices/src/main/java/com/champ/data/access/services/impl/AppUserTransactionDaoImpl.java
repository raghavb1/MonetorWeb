package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserTransaction;
import com.champ.data.access.services.IAppUserTransactionDao;
import com.champ.data.access.services.IEntityDao;

@Service("appUserTransactionDao")
public class AppUserTransactionDaoImpl implements IAppUserTransactionDao {

	@Autowired
	IEntityDao entityDao;

	@SuppressWarnings("unchecked")
	public List<AppUserTransaction> getUserTransactions(String email, String token) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select transaction from AppUserTransaction transaction left join fetch transaction.subMerchant subMerchant "
						+ "left join fetch transaction.bank bank left join fetch transaction.bankPaymentMode bankPaymentMode "
						+ "left join fetch transaction.user user where user.email =:email and user.token =:token");
		query.setParameter("email", email);
		query.setParameter("token", token);
		return (List<AppUserTransaction>) query.getResultList();
	}

}
