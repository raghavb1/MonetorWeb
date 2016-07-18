package com.champ.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.champ.core.cache.AppUserBankCache;
import com.champ.core.cache.AppUserCache;
import com.champ.core.cache.BankCache;
import com.champ.core.cache.PaymentModeCache;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.cache.SubMerchantCache;
import com.champ.core.dto.SearchQueryParserDto;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.Bank;
import com.champ.core.entity.BankPaymentMode;
import com.champ.core.entity.Parser;
import com.champ.core.entity.SearchQuery;
import com.champ.core.entity.SubMerchant;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.data.access.services.IPropertyDao;
import com.champ.gmail.api.client.IGmailClientService;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserService;
import com.champ.services.IBankPaymentModeService;
import com.champ.services.IBankService;
import com.champ.services.IStartupService;
import com.champ.services.ISubMerchantService;
import com.champ.services.executors.TransactionExecutorService;
import com.champ.services.executors.TransactionExecutorServiceWrapper;
import com.champ.services.thread.CacheReloadThread;
import com.champ.services.thread.UserBatchThread;

@Service
public class StartupServiceImpl implements IStartupService, ApplicationContextAware {

	@Autowired
	IPropertyDao propertyDao;

	@Autowired
	IBankPaymentModeService bankPaymentModeService;

	@Autowired
	IAppUserService appUserService;

	@Autowired
	IAppUserBankService appUserBankService;

	@Autowired
	IGmailClientService gmailClientService;

	@Autowired
	IBankService bankService;

	@Autowired
	TransactionExecutorServiceWrapper transactionExecutorServiceWrapper;

	@Autowired
	ISubMerchantService subMerchantService;

	private ScheduledExecutorService cacheReloadExecutor = Executors.newScheduledThreadPool(2);

	private ApplicationContext applicationContext;

	private static final Logger LOG = LoggerFactory.getLogger(StartupServiceImpl.class);

	public void loadContext() {
		LOG.info("Startup Service Called");
		loadProperties();
		loadPaymentModeCache();
		loadAppUserBanks();
		loadBankCache();
		loadTransactionExecutor();
		loadSubmerchantsCache();
		scheduleCacheReload();
		LOG.info("Startup Service Completed");
	}

	public void loadProperties() {
		LOG.info("Loading properties from Enum");
		PropertyMapCache cache = new PropertyMapCache();
		for (Property p : Property.values()) {
			cache.addProperty(p.getName(), p.getValue());
		}
		LOG.info("Loaded properties from enum. Getting properties from DB");
		List<com.champ.core.entity.Property> dbProperties = propertyDao.getAllProperties();
		if (dbProperties != null && dbProperties.size() > 0) {
			for (com.champ.core.entity.Property property : dbProperties) {
				cache.addProperty(property.getName(), property.getValue());
			}
		}
		LOG.info("Loaded properties from DB");
		CacheManager.getInstance().setCache(cache);
		LOG.info("Loaded properties.");

	}

	public void loadPaymentModeCache() {
		LOG.info("Loading Bank Payment Mode Cache");
		List<BankPaymentMode> bankPaymentModes = bankPaymentModeService.getAllBankPaymentModes();
		PaymentModeCache paymentModeCache = new PaymentModeCache();
		if (bankPaymentModes != null && bankPaymentModes.size() > 0) {
			for (BankPaymentMode bankPaymentMode : bankPaymentModes) {
				paymentModeCache.addPaymentMode(bankPaymentMode);
			}
		}
		CacheManager.getInstance().setCache(paymentModeCache);
		LOG.info("Loaded Bank Payment Mode Cache");
	}

	public void loadAppUserBanks() {
		LOG.info("Loading App Users and User Banks Cache");
		List<AppUser> users = appUserService.getAllUsers();
		AppUserBankCache cache = new AppUserBankCache();
		AppUserCache userCache = new AppUserCache();
		if (users != null && users.size() > 0) {
			for (AppUser user : users) {
				userCache.addUser(user);
				List<Bank> banks = appUserBankService.getBanksForUser(user.getEmail(), user.getToken());
				if (banks != null) {
					LOG.info("{} Banks found for user {}", banks.size(), user.getEmail());
					cache.addAppUserBank(user.getEmail(), user.getToken(), banks);
				} else {
					LOG.info("No banks found for user {}", user.getEmail());
				}
			}
		}
		CacheManager.getInstance().setCache(cache);
		CacheManager.getInstance().setCache(userCache);
		LOG.info("Loaded App Users and User Banks Cache");
	}

	private void scheduleCacheReload() {
		LOG.info("Scheduling Cache Reload");
		int initialDelay = getMinutesToNextCompleteHour();
		int reloadPeriod = CacheManager.getInstance().getCache(PropertyMapCache.class)
				.getPropertyInteger(Property.CACHE_RELOAD_PERIOD);
		CacheReloadThread thread = new CacheReloadThread(this);
		cacheReloadExecutor.scheduleAtFixedRate(thread, initialDelay, reloadPeriod, TimeUnit.MINUTES);
		if (CacheManager.getInstance().getCache(PropertyMapCache.class)
				.getPropertyBoolean(Property.ENABLE_MESSAGE_TASK)) {
			UserBatchThread userBatchThread = new UserBatchThread(transactionExecutorServiceWrapper, appUserService);
			int frequency = CacheManager.getInstance().getCache(PropertyMapCache.class)
					.getPropertyInteger(Property.MESSAGES_FETCH_PERIOD);
			cacheReloadExecutor.scheduleAtFixedRate(userBatchThread, 0, frequency, TimeUnit.MINUTES);
		}
		LOG.info("Scheduled Cache Reloader");
	}

	private Integer getMinutesToNextCompleteHour() {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		return 60 - currentCalendar.get(Calendar.MINUTE);
	}

	public void reloadContext() {
		LOG.info("Context Reload Initiated");
		loadProperties();
		loadPaymentModeCache();
		loadAppUserBanks();
		loadBankCache();
		loadTransactionExecutor();
		loadSubmerchantsCache();
		LOG.info("Context Reload Completed");
	}

	public void loadBankCache() {
		LOG.info("Loading Banks Cache");
		List<Bank> banks = bankService.getAllEnabledBanks();
		BankCache cache = new BankCache();
		if (banks != null && banks.size() > 0) {
			for (Bank bank : banks) {
				List<SearchQuery> searchQueries = bankService.getSearchQueryForBank(bank.getId());
				if (searchQueries != null && searchQueries.size() > 0) {
					for (SearchQuery query : searchQueries) {
						Parser parser = bankService.getParserForSearchQuery(query.getId());
						cache.addBank(bank, new SearchQueryParserDto(query, parser));
					}
				} else {
					LOG.info("No Search Queries found for Bank {}. Adding only bank in cache", bank.getName());
					cache.addBank(bank, null);
				}
				List<BankPaymentMode> bankPaymentModes = bankPaymentModeService
						.findBankPaymentModesByBankId(bank.getId());
				if (bankPaymentModes != null && bankPaymentModes.size() > 0) {
					for (BankPaymentMode mode : bankPaymentModes) {
						cache.addBankPaymentModes(bank.getName(), mode);
					}
				}
			}
		} else {
			LOG.info("Banks not found in system");
		}
		CacheManager.getInstance().setCache(cache);
		LOG.info("Loaded Banks Cache");
	}

	public void loadTransactionExecutor() {
		LOG.info("Loading Transaction Executor Service");
		if (transactionExecutorServiceWrapper.getTransactionExecutorService() != null) {
			if (transactionExecutorServiceWrapper.getTransactionExecutorService()
					.getCoreThreadPoolSize() != CacheManager.getInstance().getCache(PropertyMapCache.class)
							.getPropertyInteger(Property.TRANSACTION_EXECUTOR_CORE_THREAD_POOL)
					|| transactionExecutorServiceWrapper.getTransactionExecutorService()
							.getBlockingQueueSize() != CacheManager.getInstance().getCache(PropertyMapCache.class)
									.getPropertyInteger(Property.TRANSACTION_EXECUTOR_BLOCKING_QUEUE_SIZE)
					|| transactionExecutorServiceWrapper.getTransactionExecutorService()
							.getMaxThreadPoolSize() != CacheManager.getInstance().getCache(PropertyMapCache.class)
									.getPropertyInteger(Property.TRANSACTION_EXECUTOR_MAX_THREAD_POOL)
					|| transactionExecutorServiceWrapper.getTransactionExecutorService()
							.getKeepAliveTime() != CacheManager.getInstance().getCache(PropertyMapCache.class)
									.getPropertyInteger(Property.TRANSACTION_EXECUTOR_KEEP_ALIVE_TIME)
					|| transactionExecutorServiceWrapper.getTransactionExecutorService()
							.getRejectionWaitingTime() != CacheManager.getInstance().getCache(PropertyMapCache.class)
									.getPropertyInteger(Property.TRANSACTION_EXECUTOR_REJECTION_TIME)) {
				LOG.info("Loading new Transaction Executor Service as there is change in configuration");
				TransactionExecutorService transactionExecutorService = new TransactionExecutorService(
						CacheManager.getInstance().getCache(PropertyMapCache.class)
								.getPropertyInteger(Property.TRANSACTION_EXECUTOR_CORE_THREAD_POOL),
						CacheManager.getInstance().getCache(PropertyMapCache.class)
								.getPropertyInteger(Property.TRANSACTION_EXECUTOR_MAX_THREAD_POOL),
						CacheManager.getInstance().getCache(PropertyMapCache.class)
								.getPropertyInteger(Property.TRANSACTION_EXECUTOR_KEEP_ALIVE_TIME),
						CacheManager.getInstance().getCache(PropertyMapCache.class)
								.getPropertyInteger(Property.TRANSACTION_EXECUTOR_REJECTION_TIME),
						CacheManager.getInstance().getCache(PropertyMapCache.class)
								.getPropertyInteger(Property.TRANSACTION_EXECUTOR_BLOCKING_QUEUE_SIZE),
						transactionExecutorServiceWrapper.getTransactionService(),
						transactionExecutorServiceWrapper.getGmailClient(),
						transactionExecutorServiceWrapper.getAppUserBankService(),
						transactionExecutorServiceWrapper.getAppUserService());
				transactionExecutorServiceWrapper.getTransactionExecutorService().shutDown();
				transactionExecutorServiceWrapper.setTransactionExecutorService(transactionExecutorService);
			} else {
				LOG.info("Transaction Executor Service running with following configuration. Thread pool size : "
						+ transactionExecutorServiceWrapper.getTransactionExecutorService().getCoreThreadPoolSize()
						+ " and Blocking Queue Size : "
						+ transactionExecutorServiceWrapper.getTransactionExecutorService().getBlockingQueueSize());
			}
		} else {
			LOG.info("No Transaction Executor Service exists. Creating a new one....");
			TransactionExecutorService transactionExecutorService = new TransactionExecutorService(
					CacheManager.getInstance().getCache(PropertyMapCache.class)
							.getPropertyInteger(Property.TRANSACTION_EXECUTOR_CORE_THREAD_POOL),
					CacheManager.getInstance().getCache(PropertyMapCache.class)
							.getPropertyInteger(Property.TRANSACTION_EXECUTOR_MAX_THREAD_POOL),
					CacheManager.getInstance().getCache(PropertyMapCache.class)
							.getPropertyInteger(Property.TRANSACTION_EXECUTOR_KEEP_ALIVE_TIME),
					CacheManager.getInstance().getCache(PropertyMapCache.class)
							.getPropertyInteger(Property.TRANSACTION_EXECUTOR_REJECTION_TIME),
					CacheManager.getInstance().getCache(PropertyMapCache.class)
							.getPropertyInteger(Property.TRANSACTION_EXECUTOR_BLOCKING_QUEUE_SIZE),
					transactionExecutorServiceWrapper.getTransactionService(),
					transactionExecutorServiceWrapper.getGmailClient(),
					transactionExecutorServiceWrapper.getAppUserBankService(),
					transactionExecutorServiceWrapper.getAppUserService());
			transactionExecutorServiceWrapper.setTransactionExecutorService(transactionExecutorService);
		}
	}

	public void loadSubmerchantsCache() {
		LOG.info("Loading submerchant cache");
		List<SubMerchant> subMerchants = subMerchantService.getAllSubMerchants();
		LOG.info("{} Submerchants found from DB", subMerchants.size());
		SubMerchantCache cache = new SubMerchantCache();
		if (subMerchants != null && subMerchants.size() > 0) {
			for (SubMerchant subMerchant : subMerchants) {
				cache.addSubMerchant(subMerchant);
			}
		}
		CacheManager.getInstance().setCache(cache);
		LOG.info("Loaded Submerchants Cache");
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
