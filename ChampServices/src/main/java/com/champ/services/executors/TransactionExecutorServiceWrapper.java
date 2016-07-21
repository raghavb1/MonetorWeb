package com.champ.services.executors;

import com.champ.gmail.api.client.IGmailClientService;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserService;
import com.champ.services.ITransactionService;

public class TransactionExecutorServiceWrapper {

	private static TransactionExecutorServiceWrapper instance = null;

	private ITransactionService transactionService;

	private IGmailClientService gmailClient;

	private IAppUserBankService appUserBankService;

	private IAppUserService appUserService;

	private TransactionExecutorService transactionExecutorService;

	private TransactionExecutorServiceWrapper() {
		super();
	}

	public static TransactionExecutorServiceWrapper getInstance() {
		if (instance == null) {
			synchronized (TransactionExecutorServiceWrapper.class) {
				if (instance == null) {
					instance = new TransactionExecutorServiceWrapper();
				}
			}
		}
		return instance;
	}

	public ITransactionService getTransactionService() {
		return transactionService;
	}

	public void setTransactionService(ITransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public IGmailClientService getGmailClient() {
		return gmailClient;
	}

	public void setGmailClient(IGmailClientService gmailClient) {
		this.gmailClient = gmailClient;
	}

	public IAppUserBankService getAppUserBankService() {
		return appUserBankService;
	}

	public void setAppUserBankService(IAppUserBankService appUserBankService) {
		this.appUserBankService = appUserBankService;
	}

	public IAppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(IAppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public TransactionExecutorService getTransactionExecutorService() {
		return transactionExecutorService;
	}

	public void setTransactionExecutorService(TransactionExecutorService transactionExecutorService) {
		this.transactionExecutorService = transactionExecutorService;
	}

}
