package com.champ.web.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.champ.core.entity.AdminUser;
import com.champ.services.IAdminUserManagementService;

@Service
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	IAdminUserManagementService adminUserManagementService;

	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		AdminUser user = adminUserManagementService.getUserByEmail(arg0);
		if (user != null) {
			return user;
		} else {
			return new AdminUser();
		}
	}

}
