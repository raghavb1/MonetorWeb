package com.champ.services.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.SignupResponse;
import com.champ.base.response.UserBank;
import com.champ.core.cache.AppUserBankCache;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.core.enums.ApiResponseCodes;
import com.champ.core.enums.Property;
import com.champ.core.exception.MonetorServiceException;
import com.champ.core.utility.CacheManager;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;
import com.champ.services.IApiService;
import com.champ.services.IAppUserService;
import com.champ.services.IAppUserTransactionService;
import com.champ.services.IConverterService;

@Service("apiService")
@Transactional
public class ApiServiceImpl implements IApiService {

	@Autowired
	IAppUserService appUserService;

	@Autowired
	IConverterService converterService;

	@Autowired
	IAppUserTransactionService appUserTransactionService;

	private static final Logger LOG = LoggerFactory.getLogger(ApiServiceImpl.class);

	public SignupResponse signup(GmailTokensResponse request, UserInfoResponse userInfo) throws Exception {
		SignupResponse response = new SignupResponse();
		AppUser user = null;
		if (!appUserService.checkUser(userInfo.getEmail())) {
			user = appUserService.getUserByEmail(userInfo.getEmail());
		}
		user = converterService.getUserFromRequest(request, userInfo,user);
		user = appUserService.saveOrUpdateUser(user);
		response.setEmail(user.getEmail());
		response.setToken(user.getToken());
		return response;
	}

	public SignupResponse signin(BaseRequest request) throws Exception {
		SignupResponse response = new SignupResponse();
		AppUser user = appUserService.authenticateUser(request.getEmail(), request.getToken());
		if (user != null) {
			if (DateUtils.isDatePassed(user.getTokenExpiryTime())) {
				user.setToken(converterService.generateTokenForUser());
				user.setTokenExpiryTime(DateUtils.addToDate(new Date(), TimeUnit.SECONDS, CacheManager.getInstance()
						.getCache(PropertyMapCache.class).getPropertyInteger(Property.TOKEN_EXPIRY_UPDATE_SECONDS)));
				user = appUserService.saveOrUpdateUser(user);
			}
			response.setEmail(user.getEmail());
			response.setToken(user.getToken());
		} else {
			LOG.info("User not found for email {}", request.getEmail());
			throw new MonetorServiceException(ApiResponseCodes.USER_NOT_FOUND);
		}
		return response;
	}

	public GetUserBankResponse getBanksForUser(GetUserBanksRequest request) throws Exception {
		GetUserBankResponse response = new GetUserBankResponse();
		AppUserBankCache cache = CacheManager.getInstance().getCache(AppUserBankCache.class);
		if (cache != null) {
			List<Bank> banks = cache.getBanksForUserByEmailAndToken(request.getEmail(), request.getToken());
			if (banks != null && banks.size() > 0) {
				List<UserBank> userBanks = converterService.getUserBankFromBanks(banks);
				response.setUserBanks(userBanks);
			} else {
				LOG.info("Banks not found for user {}", request.getEmail());
				throw new MonetorServiceException(ApiResponseCodes.BANK_NOT_FOUND);
			}
		} else {
			LOG.info("Cache not found");
			throw new MonetorServiceException(ApiResponseCodes.CACHE_NOT_FOUND);
		}
		return response;
	}

	public GetUserTransactionResponse getTransactionsForUser(GetUserTransactionRequest request) throws Exception {
		GetUserTransactionResponse response = new GetUserTransactionResponse();
		List<AppUserTransaction> transactions = appUserTransactionService.getUserTransactions(request.getEmail(),
				request.getToken());
		if (transactions != null && transactions.size() > 0) {
			response.setUserTransactions(converterService.getUserTransactions(transactions));
		} else {
			LOG.info("Transactions not found for user {}", request.getEmail());
			throw new MonetorServiceException(ApiResponseCodes.USER_TRANSACTIONS_NOT_FOUND);
		}
		return response;
	}

}
