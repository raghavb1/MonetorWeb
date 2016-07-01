package com.champ.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.cache.AppUserBankCache;
import com.champ.core.cache.PaymentModeCache;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.Bank;
import com.champ.core.entity.BankPaymentMode;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.data.access.services.IPropertyDao;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserService;
import com.champ.services.IBankPaymentModeService;
import com.champ.services.IStartupService;
import com.champ.services.thread.CacheReloadThread;

@Service
public class StartupServiceImpl implements IStartupService {

	@Autowired
	IPropertyDao propertyDao;

	@Autowired
	IBankPaymentModeService bankPaymentModeService;

	@Autowired
	IAppUserService appUserService;

	@Autowired
	IAppUserBankService appUserBankService;

	private ScheduledExecutorService cacheReloadExecutor = Executors.newScheduledThreadPool(1);

	private static final Logger LOG = LoggerFactory.getLogger(StartupServiceImpl.class);

	public void loadContext() {
		LOG.info("Startup Service Called");
		loadProperties();
		loadPaymentModeCache();
		loadAppUserBanks();
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
		LOG.info("Loading App User Banks Cache");
		List<AppUser> users = appUserService.getAllUsers();
		AppUserBankCache cache = new AppUserBankCache();
		if (users != null && users.size() > 0) {
			for (AppUser user : users) {
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
		LOG.info("Loaded App User Banks Cache");
	}

	private void scheduleCacheReload() {
		LOG.info("Scheduling Cache Reload");
		int initialDelay = getMinutesToNextCompleteHour();
		int reloadPeriod = CacheManager.getInstance().getCache(PropertyMapCache.class)
				.getPropertyInteger(Property.CACHE_RELOAD_PERIOD);
		CacheReloadThread thread = new CacheReloadThread(this);
		cacheReloadExecutor.scheduleAtFixedRate(thread, initialDelay, reloadPeriod, TimeUnit.MINUTES);
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
		LOG.info("Context Reload Completed");
	}

}
