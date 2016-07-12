package com.champ.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.entity.AppUser;

@Cache(name = "appUserCache")
public class AppUserCache {

	private Map<String, AppUser> emailToUser = new HashMap<String, AppUser>();
	private List<AppUser> users = new ArrayList<AppUser>();

	public void addUser(AppUser user) {
		emailToUser.put(user.getEmail(), user);
		users.add(user);
	}

	public void updateUser(AppUser user) {
		emailToUser.put(user.getEmail(), user);
		users.remove(user);
		users.add(user);
	}

	public List<AppUser> getAllUsers() {
		return users;
	}

	public AppUser getUserByEmail(String email) {
		return emailToUser.get(email);
	}
}
