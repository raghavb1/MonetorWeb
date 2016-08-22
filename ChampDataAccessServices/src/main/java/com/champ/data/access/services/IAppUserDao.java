package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.AppUser;

public interface IAppUserDao {

	public AppUser getUserByMobile(String mobile);

	public boolean checkUser(String mobile);

	public AppUser saveOrUpdateUser(AppUser user);
	
	public List<AppUser> getAllUsers();
	
	public AppUser authenticateUser(String mobile, String password);
}
