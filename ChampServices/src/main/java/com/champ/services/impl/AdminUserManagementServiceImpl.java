package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AdminUser;
import com.champ.core.entity.Role;
import com.champ.data.access.services.IAdminUserManagementDao;
import com.champ.services.IAdminUserManagementService;

@Transactional
@Service("adminUserManagementService")
public class AdminUserManagementServiceImpl implements IAdminUserManagementService {

	@Autowired
	IAdminUserManagementDao adminUserManagementDao;

	public AdminUser getUserByEmail(String email) {
		return adminUserManagementDao.getUserByEmail(email);
	}

	public List<Role> getAllRoles() {
		return adminUserManagementDao.getAllRoles();
	}

	public void saveOrUpdateRole(Role role) {
		adminUserManagementDao.saveOrUpdateRole(role);
	}

	public AdminUser findUserById(Long id) {
		return adminUserManagementDao.findUserById(id);
	}

	public void saveOrUpdateUser(AdminUser user) {
		if (user.getId() == null) {
			user.setPassword(getHashedPassword(user.getPassword()));
		}
		adminUserManagementDao.saveOrUpdateUser(user);
	}

	private String getHashedPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	public List<AdminUser> getUsersExceptLoggedIn(String email) {
		return adminUserManagementDao.getUsersExceptLoggedIn(email);
	}

	public void enableUser(Long id) {
		adminUserManagementDao.enableUser(id);
	}

	public void disableUser(Long id) {
		adminUserManagementDao.disableUser(id);
	}

	public boolean checkUser(String email) {
		return adminUserManagementDao.checkUser(email);
	}

}
