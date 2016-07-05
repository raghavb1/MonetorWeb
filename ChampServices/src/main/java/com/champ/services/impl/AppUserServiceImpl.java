package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AppUser;
import com.champ.data.access.services.IAppUserDao;
import com.champ.services.IAppUserService;

@Service("appUserService")
@Transactional
public class AppUserServiceImpl implements IAppUserService {

	@Autowired
	IAppUserDao appUserDao;

	public AppUser getUserByEmail(String email) {
		return appUserDao.getUserByEmail(email);
	}

	public boolean checkUser(String email) {
		return appUserDao.checkUser(email);
	}

	public AppUser saveOrUpdateUser(AppUser user) {
		return appUserDao.saveOrUpdateUser(user);
	}

	public List<AppUser> getAllUsers() {
		return appUserDao.getAllUsers();
	}

	public AppUser authenticateUser(String email, String token) {
		return appUserDao.authenticateUser(email, token);
	}

}
