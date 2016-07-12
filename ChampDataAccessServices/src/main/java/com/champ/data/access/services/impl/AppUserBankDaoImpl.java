package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUserBank;
import com.champ.core.entity.Bank;
import com.champ.core.utility.ConverterUtils;
import com.champ.data.access.services.IAppUserBankDao;
import com.champ.data.access.services.IEntityDao;

@Service("appUserBankDao")
public class AppUserBankDaoImpl implements IAppUserBankDao {

	@Autowired
	IEntityDao entityDao;

	private final static Logger LOG = LoggerFactory.getLogger(AppUserBankDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<Bank> getBanksForUser(String email, String token) {
		Query query = entityDao.getEntityManager().createQuery(
				"Select appUserBank from AppUserBank appUserBank left join fetch appUserBank.user appUser where appUser.email =:email and appUser.token =:token");
		query.setParameter("email", email);
		query.setParameter("token", token);
		List<AppUserBank> appUserBanks = (List<AppUserBank>) query.getResultList();
		try {
			return (List<Bank>) ConverterUtils.getFieldListFromDTOList(appUserBanks, "bank", AppUserBank.class);
		} catch (Exception ex) {
			LOG.error("Error while getting bank list from app user banks", ex);
			return null;
		}

	}

	public AppUserBank saveUserBank(AppUserBank appUserBank) {
		return entityDao.saveOrUpdate(appUserBank);
	}

}
