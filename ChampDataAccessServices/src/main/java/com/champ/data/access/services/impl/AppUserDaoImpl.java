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

	public AppUser getUserByEmail(String email) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user from AppUser user where user.email =:email");
		query.setParameter("email", email);
		return (AppUser) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public boolean checkUser(String email) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user.email from AppUser user where user.email = :email");
		query.setParameter("email", email);
		List<String> emails = (List<String>) query.getResultList();
		if (emails.size() > 0) {
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
	public AppUser authenticateUser(String email, String token) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user from AppUser user where user.email =:email and user.token =:token");
		query.setParameter("email", email);
		query.setParameter("token", token);
		List<AppUser> users = (List<AppUser>) query.getResultList();
		if(users != null && users.size() > 0){
			return users.get(0);
		}else{
			return null;
		}
	}

}
