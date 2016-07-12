package com.champ.services.thread;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.champ.core.cache.PropertyMapCache;
import com.champ.core.entity.AppUser;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.services.IAppUserService;
import com.champ.services.executors.TransactionExecutorServiceWrapper;

public class UserBatchThread extends Thread {

	private IAppUserService appUserService;
	private TransactionExecutorServiceWrapper transactionExecutorServiceWrapper;

	private static final Logger LOG = LoggerFactory.getLogger(UserBatchThread.class);

	public void run() {
		List<AppUser> users = appUserService.getAllUsers();
		if (users != null && users.size() > 0) {
			LOG.info("Users found for splitting and getting transactions");
			int batchSize = CacheManager.getInstance().getCache(PropertyMapCache.class)
					.getPropertyInteger(Property.TRANSACTION_FETCH_USER_BATCH);
			List<AppUser> userBatch = new ArrayList<AppUser>();
			for (AppUser user : users) {
				userBatch.add(user);
				if (userBatch.size() == batchSize) {
					LOG.info("Creating a thread with complete batch of {} users", batchSize);
					transactionExecutorServiceWrapper.getTransactionExecutorService().executeTask(userBatch);
					userBatch.clear();
				}
			}
			if (userBatch.size() > 0) {
				LOG.info("Creating a thread with {} users", userBatch.size());
				transactionExecutorServiceWrapper.getTransactionExecutorService().executeTask(userBatch);
			}
		}
	}

	public UserBatchThread(TransactionExecutorServiceWrapper transactionExecutorServiceWrapper,
			IAppUserService appUserService) {
		super();
		this.transactionExecutorServiceWrapper = transactionExecutorServiceWrapper;
		this.appUserService = appUserService;
	}

}
