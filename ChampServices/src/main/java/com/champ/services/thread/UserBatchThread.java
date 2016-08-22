package com.champ.services.thread;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.champ.core.cache.PropertyMapCache;
import com.champ.core.entity.AppUserLinkedAccount;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.services.IAppUserLinkedAccountService;
import com.champ.services.executors.TransactionExecutorServiceWrapper;

public class UserBatchThread implements Runnable {

	private IAppUserLinkedAccountService appUserLinkedAccountService;
	private TransactionExecutorServiceWrapper transactionExecutorServiceWrapper;

	private static final Logger LOG = LoggerFactory.getLogger(UserBatchThread.class);

	public void run() {
		List<AppUserLinkedAccount> linkedAccounts = appUserLinkedAccountService.getAllLinkedAccounts();
		if (!CollectionUtils.isEmpty(linkedAccounts)) {
			LOG.info("Linked Accounts found for splitting and getting transactions");
			int batchSize = CacheManager.getInstance().getCache(PropertyMapCache.class)
					.getPropertyInteger(Property.TRANSACTION_FETCH_USER_BATCH);
			List<AppUserLinkedAccount> accountBatch = new ArrayList<AppUserLinkedAccount>();
			for (AppUserLinkedAccount account : linkedAccounts) {
				accountBatch.add(account);
				if (accountBatch.size() == batchSize) {
					transactionExecutorServiceWrapper.getTransactionExecutorService().executeTask(accountBatch);
					accountBatch = new ArrayList<AppUserLinkedAccount>();
				}
			}
			if (accountBatch.size() > 0) {
				LOG.info("Creating a thread with {} accounts", accountBatch.size());
				transactionExecutorServiceWrapper.getTransactionExecutorService().executeTask(accountBatch);
			}
		} else {
			LOG.info("No Users found to fetch messages");
		}
	}

	public UserBatchThread(TransactionExecutorServiceWrapper transactionExecutorServiceWrapper,
			IAppUserLinkedAccountService appUserLinkedAccountService) {
		super();
		this.transactionExecutorServiceWrapper = transactionExecutorServiceWrapper;
		this.appUserLinkedAccountService = appUserLinkedAccountService;
	}

}
