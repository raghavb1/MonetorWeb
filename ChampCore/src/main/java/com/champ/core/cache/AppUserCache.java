package com.champ.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.entity.AppUser;

@Cache(name = "appUserCache")
public class AppUserCache {

	private Map<String, AppUser> mobileToUser = new HashMap<String, AppUser>();
	private List<AppUser> users = new ArrayList<AppUser>();

	public void addUser(AppUser user) {
		mobileToUser.put(user.getMobile(), user);
		users.add(user);
	}

	public void updateUser(AppUser user) {
		mobileToUser.put(user.getMobile(), user);
		users.remove(user);
		users.add(user);
	}

	public List<AppUser> getAllUsers() {
		return users;
	}

	public AppUser getUserByMobile(String mobile) {
		return mobileToUser.get(mobile);
	}
}
