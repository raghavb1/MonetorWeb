package com.champ.services;

import java.util.List;

import com.champ.core.entity.AppUser;

public interface IAppUserService {

	public AppUser getUserByEmail(String email);

	public boolean checkUser(String email);

	public AppUser saveOrUpdateUser(AppUser user);
	
	public List<AppUser> getAllUsers();
	
	public AppUser authenticateUser(String email, String token);
}
