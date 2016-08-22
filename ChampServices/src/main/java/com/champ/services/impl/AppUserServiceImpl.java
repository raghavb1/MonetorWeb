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

	public AppUser getUserByMobile(String mobile) {
		return appUserDao.getUserByMobile(mobile);
	}

	public boolean checkUser(String mobile) {
		return appUserDao.checkUser(mobile);
	}

	public AppUser saveOrUpdateUser(AppUser user) {
		return appUserDao.saveOrUpdateUser(user);
	}

	public List<AppUser> getAllUsers() {
		return appUserDao.getAllUsers();
	}

	public AppUser authenticateUser(String mobile, String token) {
		return appUserDao.authenticateUser(mobile, token);
	}

}
