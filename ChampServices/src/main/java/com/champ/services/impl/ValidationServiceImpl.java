package com.champ.services.impl;

import org.springframework.stereotype.Service;

import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.services.IValidationService;

@Service("validationService")
public class ValidationServiceImpl implements IValidationService {

	public boolean validateSigninCall(BaseRequest request) {
		if (request == null) {
			return false;
		}
		String authenticationKey = CacheManager.getInstance().getCache(PropertyMapCache.class)
				.getPropertyString(Property.API_AUTHENTICATION_KEY);
		if (!authenticationKey.equals(request.getAuthenticationKey())) {
			return false;
		}
		if (request.getToken() == null || request.getEmail() == null) {
			return false;
		}
		return true;
	}

	public boolean validateCallToGetBanksForUser(GetUserBanksRequest request) {
		if (request == null) {
			return false;
		}
		String authenticationKey = CacheManager.getInstance().getCache(PropertyMapCache.class)
				.getPropertyString(Property.API_AUTHENTICATION_KEY);
		if (!authenticationKey.equals(request.getAuthenticationKey())) {
			return false;
		}
		if (request.getEmail() == null || request.getToken() == null) {
			return false;
		}
		return true;
	}

	public boolean validateCallToGetTransactionsForUser(GetUserTransactionRequest request) {
		if (request == null) {
			return false;
		}
		String authenticationKey = CacheManager.getInstance().getCache(PropertyMapCache.class)
				.getPropertyString(Property.API_AUTHENTICATION_KEY);
		if (!authenticationKey.equals(request.getAuthenticationKey())) {
			return false;
		}
		if (request.getEmail() == null || request.getToken() == null) {
			return false;
		}
		return true;
	}

}
