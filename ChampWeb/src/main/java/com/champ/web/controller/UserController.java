
package com.champ.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.champ.core.entity.AdminUser;
import com.champ.core.entity.Role;
import com.champ.services.IAdminUserManagementService;

/** Manages complete user operation **/
@Controller
@RequestMapping("/User")
public class UserController {

	@Autowired
	IAdminUserManagementService adminUserManagementService;

	@RequestMapping("/create")
	public String addUser(ModelMap map) {
		AdminUser user = new AdminUser();
		List<Role> userRoles = adminUserManagementService.getAllRoles();
		map.put("roles", userRoles);
		map.put("user", user);
		return "User/create";
	}

	@RequestMapping("/role/create")
	public String addRoles(ModelMap map) {
		Role role = new Role();
		map.put("role", role);
		return "User/createRole";
	}

	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	public String saveRole(@ModelAttribute("role") Role role, ModelMap map) {
		adminUserManagementService.saveOrUpdateRole(role);
		Role newRole = new Role();
		map.put("role", newRole);
		map.put("message", "Role Saved Successfully");
		return "User/createRole";
	}

	@RequestMapping("/save")
	public String saveUser(@ModelAttribute("user") AdminUser user, ModelMap map,
			@RequestParam(value = "role[]") Long[] userRoles) {
		if (userRoles != null && userRoles.length > 0) {
			List<Role> finalRoles = new ArrayList<Role>();
			for (Long roleId : userRoles) {
				Role r = new Role();
				r.setId(roleId);
				finalRoles.add(r);
			}
			if (user.getId() != null) {
				user = adminUserManagementService.findUserById(user.getId());
			}
			user.setRoles(finalRoles);
			adminUserManagementService.saveOrUpdateUser(user);
			List<Role> userRoleList = adminUserManagementService.getAllRoles();
			map.put("roles", userRoleList);
			map.put("user", new AdminUser());
			map.put("message", "User saved Successfully");
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			List<AdminUser> userList = adminUserManagementService.getUsersExceptLoggedIn(email);
			map.put("users", userList);
			return "User/view";
		} else {
			List<Role> userRolesList = adminUserManagementService.getAllRoles();
			map.put("roles", userRolesList);
			map.put("user", user);
			map.put("edit", true);
			map.put("message", "Please select role and warehouse for the user.");
			return "User/create";
		}
	}

	@RequestMapping("/view")
	public String viewAllUser(ModelMap map) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<AdminUser> userList = adminUserManagementService.getUsersExceptLoggedIn(userName);
		map.put("users", userList);
		return "User/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") Long id, ModelMap map) {
		AdminUser user = adminUserManagementService.findUserById(id);
		List<Role> userRoles = adminUserManagementService.getAllRoles();
		map.put("roles", userRoles);
		map.put("user", user);
		map.put("edit", true);
		return "User/create";
	}

	@RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
	public String disableUser(@PathVariable("id") Long id, ModelMap map) {
		adminUserManagementService.disableUser(id);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<AdminUser> userList = adminUserManagementService.getUsersExceptLoggedIn(userName);
		map.put("users", userList);
		map.put("message", "User disabled Successfully");
		return "User/view";
	}

	@RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
	public String enableUser(@PathVariable("id") Long id, ModelMap map) {
		adminUserManagementService.enableUser(id);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<AdminUser> userList = adminUserManagementService.getUsersExceptLoggedIn(userName);
		map.put("users", userList);
		map.put("message", "User enabled Successfully");
		return "User/view";
	}

	@RequestMapping("/checkUser")
	public @ResponseBody String checkUser(@ModelAttribute("name") String userName) {
		boolean result = adminUserManagementService.checkUser(userName);
		if (result) {
			return "success";
		} else {
			return "failure";
		}
	}
}
