package com.champ.services.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.champ.core.cache.AppUserBankCache;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserBank;
import com.champ.core.entity.Bank;
import com.champ.core.utility.CacheManager;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.services.IAppUserBankService;
import com.champ.services.ITransactionService;

public class SaveTransactionThread implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(SaveTransactionThread.class);
	private AppUser user;
	private List<TransactionDTO> transactions;
	private Bank bank;
	private ITransactionService transactionService;
	private IAppUserBankService appUserBankService;

	public void run() {
		long startTime = System.currentTimeMillis();
		if (!CollectionUtils.isEmpty(transactions)) {
			List<Bank> banks = CacheManager.getInstance().getCache(AppUserBankCache.class)
					.getBanksForUserByMobile(user.getMobile());
			if (banks == null || !banks.contains(bank)) {
				AppUserBank appUserBank = new AppUserBank();
				appUserBank.setUser(user);
				appUserBank.setBank(bank);
				appUserBankService.saveUserBank(appUserBank);
				CacheManager.getInstance().getCache(AppUserBankCache.class).updateBankToUser(user, bank);
			}
			transactionService.saveUserTransactions(transactions, user, bank);
			LOG.info("User Transactions Saved");
		} else {
			LOG.info("Transactions not found for user {} and bank {}", user.getMobile(), bank.getName());
		}
		long endTime = System.currentTimeMillis();
		LOG.info("Time taken to save transactions {} ms", (endTime - startTime));
	}

	public SaveTransactionThread(AppUser user, List<TransactionDTO> transactions, Bank bank,
			ITransactionService transactionService, IAppUserBankService appUserBankService) {
		super();
		this.user = user;
		this.transactions = transactions;
		this.bank = bank;
		this.transactionService = transactionService;
		this.appUserBankService = appUserBankService;
	}

}
