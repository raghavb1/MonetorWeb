package com.champ.services.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.gmail.api.client.IGmailClientService;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserService;
import com.champ.services.ITransactionService;

@Service("transactionExecutorServiceWrapper")
public class TransactionExecutorServiceWrapper {

	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private IGmailClientService gmailClient;

	@Autowired
	private IAppUserBankService appUserBankService;

	@Autowired
	private IAppUserService appUserService;

	private TransactionExecutorService transactionExecutorService;

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
