package com.champ.data.access.services.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AdminUser;
import com.champ.core.entity.Role;
import com.champ.data.access.services.IAdminUserManagementDao;
import com.champ.data.access.services.IEntityDao;

@Service("adminUserManagementDao")
public class AdminUserManagementDaoImpl implements IAdminUserManagementDao {

	@Autowired
	IEntityDao entityDao;

	public AdminUser getUserByEmail(String email) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user from AdminUser user where user.email =:email and user.enabled = true");
		query.setParameter("email", email);
		return (AdminUser) query.getSingleResult();
	}

	public List<Role> getAllRoles() {
		return entityDao.findAll(Role.class);
	}

	public void saveOrUpdateRole(Role role) {
		entityDao.saveOrUpdate(role);
	}

	public AdminUser findUserById(Long id) {
		return entityDao.findById(AdminUser.class, id);
	}

	public void saveOrUpdateUser(AdminUser user) {
		entityDao.saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	public List<AdminUser> getUsersExceptLoggedIn(String email) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user from AdminUser user where user.email <> :email");
		query.setParameter("email", email);
		return (List<AdminUser>) query.getResultList();
	}

	public void enableUser(Long id) {
		AdminUser user = entityDao.findById(AdminUser.class, id);
		if (user != null) {
			user.setEnabled(true);
			entityDao.saveOrUpdate(user);
		}

	}

	public void disableUser(Long id) {
		AdminUser user = entityDao.findById(AdminUser.class, id);
		if (user != null) {
			user.setEnabled(false);
			entityDao.saveOrUpdate(user);
		}

	}

	@SuppressWarnings("unchecked")
	public boolean checkUser(String email) {
		Query query = entityDao.getEntityManager()
				.createQuery("Select user.email from AdminUser user where user.email = :email");
		query.setParameter("email", email);
		List<String> name = (List<String>) query.getResultList();
		if (name.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
