package com.champ.services.executors;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.champ.core.entity.AppUser;
import com.champ.gmail.api.client.IGmailClientService;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserService;
import com.champ.services.ITransactionService;
import com.champ.services.thread.UserTransactionThread;

public class TransactionExecutorService {

	private ThreadPoolExecutor executorService;
	private Integer coreThreadPoolSize;
	private Integer maxThreadPoolSize;
	private Integer keepAliveTime;
	private Integer rejectionWaitingTime;
	private Integer blockingQueueSize;
	private static volatile boolean executeTask = true;
	private ITransactionService transactionService;
	private IGmailClientService gmailClient;
	private IAppUserBankService appUserBankService;
	private IAppUserService appUserService;

	private static final Logger LOG = LoggerFactory.getLogger(TransactionExecutorService.class);

	public void shutDown() {
		executeTask = false;
		try {
			executorService.shutdown();
			executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			LOG.error("Error in completing all requests after shutdown.");
		}
		if (executorService.isShutdown()) {
			executeTask = true;
		} else {
			executorService.shutdownNow();
			executeTask = true;
		}
	}

	public void executeTask(List<AppUser> users) {
		if (executeTask) {
			executorService.execute(new UserTransactionThread(users, gmailClient, transactionService,
					appUserBankService, appUserService));
		}
	}

	public ThreadPoolExecutor getExecutorService() {
		return executorService;
	}

	public TransactionExecutorService(Integer coreThreadPoolSize, Integer maxThreadPoolSize, Integer keepAliveTime,
			final Integer rejectionWaitingTime, Integer blockingQueueSize, ITransactionService transactionService,
			IGmailClientService gmailClient, IAppUserBankService appUserBankService, IAppUserService appUserService) {
		super();
		this.coreThreadPoolSize = coreThreadPoolSize;
		this.maxThreadPoolSize = maxThreadPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.rejectionWaitingTime = rejectionWaitingTime;
		this.blockingQueueSize = blockingQueueSize;
		this.transactionService = transactionService;
		this.gmailClient = gmailClient;
		this.appUserBankService = appUserBankService;
		this.appUserService = appUserService;
		executorService = new ThreadPoolExecutor(coreThreadPoolSize, maxThreadPoolSize, keepAliveTime,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(blockingQueueSize));
		executorService.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executorService) {
				try {
					Thread.sleep(rejectionWaitingTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				executorService.execute(r);
			}
		});

	}

	public Integer getCoreThreadPoolSize() {
		return coreThreadPoolSize;
	}

	public void setCoreThreadPoolSize(Integer coreThreadPoolSize) {
		this.coreThreadPoolSize = coreThreadPoolSize;
	}

	public Integer getMaxThreadPoolSize() {
		return maxThreadPoolSize;
	}

	public void setMaxThreadPoolSize(Integer maxThreadPoolSize) {
		this.maxThreadPoolSize = maxThreadPoolSize;
	}

	public Integer getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(Integer keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public Integer getRejectionWaitingTime() {
		return rejectionWaitingTime;
	}

	public void setExecutorService(ThreadPoolExecutor executorService) {
		this.executorService = executorService;
	}

	public Integer getBlockingQueueSize() {
		return blockingQueueSize;
	}

	public void setBlockingQueueSize(Integer blockingQueueSize) {
		this.blockingQueueSize = blockingQueueSize;
	}

}
