package com.champ.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.champ.base.dto.FailedTransactionDto;
import com.champ.base.dto.MessageDto;
import com.champ.base.dto.UserMappedTransaction;
import com.champ.base.request.BaseRequest;
import com.champ.base.request.GetUserBanksRequest;
import com.champ.base.request.GetUserTransactionRequest;
import com.champ.base.request.RegisterUserRequest;
import com.champ.base.request.SaveMessageRequest;
import com.champ.base.request.SaveTransactionRequest;
import com.champ.base.response.BaseResponse;
import com.champ.base.response.CategoryResponse;
import com.champ.base.response.GetUserBankResponse;
import com.champ.base.response.GetUserPropertiesResponse;
import com.champ.base.response.GetUserTransactionResponse;
import com.champ.base.response.PaymentModeResponse;
import com.champ.base.response.RegisterUserResponse;
import com.champ.base.response.SaveTransactionResponse;
import com.champ.base.response.SignupResponse;
import com.champ.base.response.UserBank;
import com.champ.core.cache.AppUserBankCache;
import com.champ.core.cache.BankCache;
import com.champ.core.cache.CategoryCache;
import com.champ.core.cache.PaymentModeCache;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.cache.SearchQueryCache;
import com.champ.core.dto.PropertyMap;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserLinkedAccount;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.core.entity.Category;
import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;
import com.champ.core.enums.ApiResponseCodes;
import com.champ.core.enums.Medium;
import com.champ.core.enums.Property;
import com.champ.core.exception.MonetorServiceException;
import com.champ.core.utility.CacheManager;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;
import com.champ.services.IApiService;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserLinkedAccountService;
import com.champ.services.IAppUserService;
import com.champ.services.ICategoryService;
import com.champ.services.IConverterService;
import com.champ.services.IGmailClientService;
import com.champ.services.ISmsService;
import com.champ.services.ITransactionService;
import com.champ.services.thread.SaveTransactionThread;
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
	private ICategoryService categoryService;

	@Autowired
	private IAppUserLinkedAccountService appUserLinkedAccountService;

	@Autowired
	private ISmsService smsService;

	@Autowired
	private IAppUserBankService appUserBankService;

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	private static final Logger LOG = LoggerFactory.getLogger(ApiServiceImpl.class);

	public SignupResponse signup(GmailTokensResponse request, UserInfoResponse userInfo, AppUser user)
			throws Exception {
		SignupResponse response = new SignupResponse();
		if (user == null) {
			response.setCode(ApiResponseCodes.GMAIL_EMAIL_NOT_FOUND.getCode());
			response.setMessage(ApiResponseCodes.GMAIL_EMAIL_NOT_FOUND.getMessage());
			return response;
		}
		AppUserLinkedAccount linkedAccount = appUserLinkedAccountService.getLinkedAccountByEmail(userInfo.getEmail());
		linkedAccount = converterService.getUserFromRequest(request, userInfo, user, linkedAccount);
		if (linkedAccount == null) {
			response.setCode(ApiResponseCodes.GMAIL_EMAIL_NOT_FOUND.getCode());
			response.setMessage(ApiResponseCodes.GMAIL_EMAIL_NOT_FOUND.getMessage());
			return response;
		}
		linkedAccount = appUserLinkedAccountService.saveOrUpdateLinkedAccount(linkedAccount);
		List<AppUserLinkedAccount> accounts = new ArrayList<AppUserLinkedAccount>();
		accounts.add(linkedAccount);
		Thread pullMessageThread = new Thread(
				new UserTransactionThread(accounts, gmailClient, appUserLinkedAccountService));
		pullMessageThread.start();
		LOG.info("Thread Started to get user messages");
		response.setEmail(linkedAccount.getEmail());
		response.setName(linkedAccount.getName());
		response.setImage(linkedAccount.getImage());
		return response;
	}

	public RegisterUserResponse registerUser(RegisterUserRequest request) throws Exception {
		RegisterUserResponse response = new RegisterUserResponse();
		if (appUserService.checkUser(request.getMobile())) {
			AppUser user = new AppUser();
			user.setMobile(request.getMobile());
			user.setToken(converterService.generateTokenForUser());
			user.setCountryCode(request.getCountryCode());
			user = appUserService.saveOrUpdateUser(user);
			response.setToken(user.getToken());
		} else {
			LOG.info("User with mobile {} already registered", request.getMobile());
			throw new MonetorServiceException(ApiResponseCodes.USER_EXISTS);
		}
		return response;
	}

	public GetUserBankResponse getBanksForUser(GetUserBanksRequest request) throws Exception {
		GetUserBankResponse response = new GetUserBankResponse();
		AppUserBankCache cache = CacheManager.getInstance().getCache(AppUserBankCache.class);
		if (cache != null) {
			List<Bank> banks = cache.getBanksForUserByMobileAndToken(request.getMobile(), request.getToken());
			if (banks != null && banks.size() > 0) {
				List<UserBank> userBanks = converterService.getUserBankFromBanks(banks);
				response.setUserBanks(userBanks);
			} else {
				LOG.info("Banks not found for user {}", request.getMobile());
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
		AppUser user = appUserService.authenticateUser(request.getMobile(), request.getToken());
		if (user == null) {
			throw new MonetorServiceException(ApiResponseCodes.USER_NOT_FOUND);
		}
		List<AppUserLinkedAccount> linkedAccounts = appUserLinkedAccountService
				.getLinkedAccountsForUser(user.getMobile());
		if (!CollectionUtils.isEmpty(linkedAccounts)) {
			for (AppUserLinkedAccount account : linkedAccounts) {
				if (!account.getSynced()) {
					response.setSynced(false);
					break;
				}
			}
		}
		List<AppUserTransaction> transactions = null;
		if (request.getUserCreatedTransaction()) {
			transactions = transactionService.getUserCreatedTransactions(user.getId());
		} else {
			transactions = transactionService.getUserTransactions(user.getId());
		}
		if (transactions != null && transactions.size() > 0) {
			response.setUserTransactions(converterService.getUserTransactions(transactions));
		} else if (CollectionUtils.isEmpty(transactions) && response.getSynced()) {
			LOG.info("Transactions not found for user {}", request.getMobile());
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
		AppUser user = appUserService.authenticateUser(request.getMobile(), request.getToken());
		if (user == null) {
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
						newTransaction = transactionService.getTransactionByUserId(user.getId(),
								transaction.getTransactionId());
						if (newTransaction == null) {
							LOG.error("Transaction not found for user {} and id {}", request.getMobile(),
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
						newTransaction = converterService.getTransactionFromDto(transaction, request.getMobile());
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

	public BaseResponse saveUserMessages(SaveMessageRequest request) throws Exception {
		if (!CollectionUtils.isEmpty(request.getMessages())) {
			AppUser appUser = appUserService.getUserByMobile(request.getMobile());
			if (appUser == null) {
				throw new MonetorServiceException(ApiResponseCodes.USER_NOT_FOUND);
			}
			BankCache cache = CacheManager.getInstance().getCache(BankCache.class);
			SearchQueryCache searchQueryCache = CacheManager.getInstance().getCache(SearchQueryCache.class);
			Map<Bank, List<TransactionDTO>> transactionMap = new HashMap<Bank, List<TransactionDTO>>();
			List<SearchQuery> searchQueries = searchQueryCache.getSearchQueriesByMedium(Medium.SMS.getCode());
			int batchSize = CacheManager.getInstance().getCache(PropertyMapCache.class)
					.getPropertyInteger(Property.TRANSACTION_BATCH_SIZE);
			for (MessageDto dto : request.getMessages()) {
				for (SearchQuery query : searchQueries) {
					if (query.getSearchQuery().equalsIgnoreCase(dto.getFrom())) {
						List<Parser> parsers = cache.getParsersBySearchQueryId(query.getId());
						TransactionDTO transaction = smsService.getTransactionDtoFromSms(dto.getMessage(), parsers,
								dto.getDate());
						if (transaction != null) {
							List<TransactionDTO> dtoList = transactionMap.get(query.getBank());
							if (dtoList == null) {
								dtoList = new ArrayList<TransactionDTO>();
							}
							dtoList.add(transaction);
							if (dtoList.size() == batchSize) {
								LOG.info("Assigning Save transaction thread to executor");
								taskExecutor.submit(new SaveTransactionThread(appUser, dtoList, query.getBank(),
										transactionService, appUserBankService));
								transactionMap.put(query.getBank(), new ArrayList<TransactionDTO>());
							} else {
								transactionMap.put(query.getBank(), dtoList);
							}
							break;
						}

					}
				}
			}
			for (Map.Entry<Bank, List<TransactionDTO>> entry : transactionMap.entrySet()) {
				if (entry.getValue() != null && entry.getValue().size() > 0) {
					LOG.info("Assigning Save transaction thread to executor");
					taskExecutor.submit(new SaveTransactionThread(appUser, entry.getValue(), entry.getKey(),
							transactionService, appUserBankService));
				}
			}
		} else {
			LOG.info("No messages received to be saved");
		}
		return new BaseResponse();
	}

}
