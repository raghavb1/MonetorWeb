package com.champ.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.base.dto.FailedTransactionDto;
import com.champ.base.dto.UserMappedTransaction;
import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.request.SaveTransactionRequest;
import com.champ.base.response.CategoryResponse;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserPropertiesResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.PaymentModeResponse;
import com.champ.base.response.SaveTransactionResponse;
import com.champ.base.response.SignupResponse;
import com.champ.base.response.UserBank;
import com.champ.core.cache.AppUserBankCache;
import com.champ.core.cache.CategoryCache;
import com.champ.core.cache.PaymentModeCache;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.dto.PropertyMap;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.core.entity.Category;
import com.champ.core.enums.ApiResponseCodes;
import com.champ.core.enums.Property;
import com.champ.core.exception.MonetorServiceException;
import com.champ.core.utility.CacheManager;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.gmail.api.client.IGmailClientService;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;
import com.champ.services.IApiService;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserService;
import com.champ.services.ICategoryService;
import com.champ.services.IConverterService;
import com.champ.services.ITransactionService;
import com.champ.services.thread.UserTransactionThread;

@Service("apiService")
@Transactional
public class ApiServiceImpl implements IApiService {

	@Autowired
	IAppUserService appUserService;

	@Autowired
	IConverterService converterService;

	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private IGmailClientService gmailClient;

	@Autowired
	private IAppUserBankService appUserBankService;

	@Autowired
	private ICategoryService categoryService;

	private static final Logger LOG = LoggerFactory.getLogger(ApiServiceImpl.class);

	public SignupResponse signup(GmailTokensResponse request, UserInfoResponse userInfo) throws Exception {
		SignupResponse response = new SignupResponse();
		AppUser user = null;
		if (!appUserService.checkUser(userInfo.getEmail())) {
			user = appUserService.getUserByEmail(userInfo.getEmail());
		}
		user = converterService.getUserFromRequest(request, userInfo, user);
		if (user == null) {
			response.setCode(ApiResponseCodes.GMAIL_EMAIL_NOT_FOUND.getCode());
			response.setMessage(ApiResponseCodes.GMAIL_EMAIL_NOT_FOUND.getMessage());
			return response;
		}
		user = appUserService.saveOrUpdateUser(user);
		List<AppUser> users = new ArrayList<AppUser>();
		users.add(user);
		Thread pullMessageThread = new Thread(
				new UserTransactionThread(users, gmailClient, transactionService, appUserBankService, appUserService));
		pullMessageThread.start();
		LOG.info("Thread Started to get user messages");
		response.setEmail(user.getEmail());
		response.setToken(user.getToken());
		response.setName(user.getName());
		response.setImage(user.getImage());
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
		AppUser user = appUserService.authenticateUser(request.getEmail(), request.getToken());
		if (user == null) {
			throw new MonetorServiceException(ApiResponseCodes.USER_NOT_FOUND);
		} else if (user.getSynced() != null && !user.getSynced()) {
			response.setSynced(false);
			return response;
		}
		List<AppUserTransaction> transactions = null;
		if (request.getUserCreatedTransaction()) {
			transactions = transactionService.getUserCreatedTransactions(user.getId());
		} else {
			transactions = transactionService.getUserTransactions(user.getId());
		}
		if (transactions != null && transactions.size() > 0) {
			response.setUserTransactions(converterService.getUserTransactions(transactions));
		} else {
			LOG.info("Transactions not found for user {}", request.getEmail());
			throw new MonetorServiceException(ApiResponseCodes.USER_TRANSACTIONS_NOT_FOUND);
		}
		return response;
	}

	public GetUserPropertiesResponse getUserProperties(BaseRequest request) throws Exception {
		GetUserPropertiesResponse response = new GetUserPropertiesResponse();
		PropertyMap map = CacheManager.getInstance().getCache(PropertyMapCache.class).getProperty("user~");
		if (map != null && map.getMap() != null && map.getMap().size() > 0) {
			Map<String, String> properties = new HashMap<String, String>();
			for (Map.Entry<String, PropertyMap> entry : map.getMap().entrySet()) {
				properties.put(entry.getKey(), entry.getValue().getValue());
			}
			response.setProperties(properties);
		} else {
			LOG.info("Properties not found in Cache");
			throw new MonetorServiceException(ApiResponseCodes.USER_PROPERTIES_NOT_FOUND);
		}
		return response;
	}

	public PaymentModeResponse getPaymentModes(BaseRequest request) throws Exception {
		PaymentModeResponse response = new PaymentModeResponse();
		response.setPaymentModes(CacheManager.getInstance().getCache(PaymentModeCache.class).getPaymentModes());
		return response;
	}

	public CategoryResponse getCategories(BaseRequest request) throws Exception {
		CategoryResponse response = new CategoryResponse();
		response.setCategories(CacheManager.getInstance().getCache(CategoryCache.class).getCategories());
		return response;
	}

	public SaveTransactionResponse saveUserTransactions(SaveTransactionRequest request) throws Exception {
		SaveTransactionResponse response = new SaveTransactionResponse();
		AppUser user = appUserService.authenticateUser(request.getEmail(), request.getToken());
		if(user == null){
			throw new MonetorServiceException(ApiResponseCodes.USER_NOT_FOUND);
		}
		List<FailedTransactionDto> failureList = new ArrayList<FailedTransactionDto>();
		if (request.getTransactions() != null && request.getTransactions().size() > 0) {
			for (UserMappedTransaction transaction : request.getTransactions()) {
				try {
					if (transaction.getTransactionId() == null && (transaction.getAmount() == null
							|| transaction.getCategory() == null || transaction.getPaymentMode() == null
							|| transaction.getSubmerchantCode() == null || transaction.getTransactionDate() == null)) {
						throw new MonetorServiceException(ApiResponseCodes.INVALID_REQUEST);
					}
					AppUserTransaction newTransaction = null;
					if (transaction.getTransactionId() != null) {
						newTransaction = transactionService.getTransactionByUserId(user.getId(), transaction.getTransactionId());
						if (newTransaction == null) {
							LOG.error("Transaction not found for user {} and id {}", request.getEmail(),
									transaction.getTransactionId());
							throw new MonetorServiceException(ApiResponseCodes.TRANSACTION_NOT_FOUND);
						}
						Category category = categoryService.findCategoryByName(transaction.getCategory());
						if (category == null) {
							category = new Category();
							category.setName(transaction.getCategory());
							category.setColor(transaction.getCategoryColor());
							category.setUserDefined(true);
							category = categoryService.saveOrUpdateCategory(category);
						}
						newTransaction.setCategory(category);
						transactionService.saveTransaction(newTransaction);
					} else {
						newTransaction = converterService.getTransactionFromDto(transaction, request.getEmail());
						if (newTransaction != null) {
							transactionService.saveTransaction(newTransaction);
						}
					}
				} catch (MonetorServiceException mse) {
					failureList.add(new FailedTransactionDto(transaction.getSubmerchantCode(), transaction.getAmount(),
							mse.getMessage(), transaction.getTransactionId()));
				} catch (Exception e) {
					failureList.add(new FailedTransactionDto(transaction.getSubmerchantCode(), transaction.getAmount(),
							ApiResponseCodes.INTERNAL_SERVER_ERROR.getMessage(), transaction.getTransactionId()));
					LOG.error("Exception while saving transaction ", e);
				}
			}
			response.setFailedTransactions(failureList);
		}
		return response;
	}

}
