package com.champ.services;

import java.util.List;

import com.champ.core.entity.AdminUser;
import com.champ.core.entity.Role;

public interface IAdminUserManagementService {

	public AdminUser getUserByEmail(String email);

	public List<Role> getAllRoles();

	public void saveOrUpdateRole(Role role);

	public AdminUser findUserById(Long id);

	public void saveOrUpdateUser(AdminUser user);

	public List<AdminUser> getUsersExceptLoggedIn(String email);

	public void enableUser(Long id);

	public void disableUser(Long id);
	
	public boolean checkUser(String email);
}
