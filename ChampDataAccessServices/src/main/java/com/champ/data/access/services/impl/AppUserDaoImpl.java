package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUser;
import com.champ.data.access.services.IAppUserDao;
import com.champ.data.access.services.IEntityDao;

@Service("appUserDao")
public class AppUserDaoImpl implements IAppUserDao {

	@Autowired
	IEntityDao entityDao;

	public AppUser getUserByMobile(String mobile) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user from AppUser user where user.mobile =:mobile");
		query.setParameter("mobile", mobile);
		return (AppUser) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public boolean checkUser(String mobile) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user.mobile from AppUser user where user.mobile =:mobile");
		query.setParameter("mobile", mobile);
		List<String> mobiles = (List<String>) query.getResultList();
		if (mobiles.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public AppUser saveOrUpdateUser(AppUser user) {
		return entityDao.saveOrUpdate(user);
	}

	public List<AppUser> getAllUsers() {
		return (List<AppUser>) entityDao.findAll(AppUser.class);
	}

	@SuppressWarnings("unchecked")
	public AppUser authenticateUser(String mobile, String token) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user from AppUser user where user.mobile =:mobile and user.token =:token");
		query.setParameter("mobile", mobile);
		query.setParameter("token", token);
		List<AppUser> users = (List<AppUser>) query.getResultList();
		if(users != null && users.size() > 0){
			return users.get(0);
		}else{
			return null;
		}
	}

}
