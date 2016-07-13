package com.champ.services.thread;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.champ.core.cache.AppUserBankCache;
import com.champ.core.cache.BankCache;
import com.champ.core.dto.SearchQueryParserDto;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserBank;
import com.champ.core.entity.Bank;
import com.champ.core.utility.CacheManager;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.gmail.api.client.IGmailClientService;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.services.IAppUserBankService;
import com.champ.services.IAppUserService;
import com.champ.services.ITransactionService;

public class UserTransactionThread implements Runnable {

	private List<AppUser> users;
	private IGmailClientService gmailClient;
	private ITransactionService transactionService;
	private IAppUserBankService appUserBankService;
	private IAppUserService appUserService;

	private static final Logger LOG = LoggerFactory.getLogger(UserTransactionThread.class);

	public void run() {
		try {
			LOG.info("Thread to get data from gmail called");
			if (this.users != null && this.users.size() > 0) {
				List<Bank> banks = CacheManager.getInstance().getCache(BankCache.class).getAllBanks();
				LOG.info("Banks found from Cache {}", banks);
				for (AppUser user : this.users) {
					if (banks != null && banks.size() > 0) {
						for (Bank bank : banks) {
							getAndSaveUserTransactions(user, bank);
						}
					} else {
						LOG.info("Banks not found for user {}", user.getEmail());
					}
					user.setLastSyncedOn(DateUtils.addToDate(new Date(), TimeUnit.SECONDS, -10));
					appUserService.saveOrUpdateUser(user);
				}
			} else {
				LOG.info("Users not found to get transactions");
			}
		} catch (Exception e) {
			LOG.error("Error while getting emails", e);
		}
	}

	public UserTransactionThread() {
		super();
	}

	public void getAndSaveUserTransactions(AppUser user, Bank bank) throws Exception {
		List<SearchQueryParserDto> searchQueries = CacheManager.getInstance().getCache(BankCache.class)
				.getSearchQueryParserByBankName(bank.getName());
		if (searchQueries != null && searchQueries.size() > 0) {
			for (SearchQueryParserDto dto : searchQueries) {
				List<TransactionDTO> transactions = gmailClient.getMessages(user.getEmail(), user.getRefreshToken(),
						dto.getSearchQuery().getSearchQuery(), dto.getParser().getTemplate());
				if (transactions != null && transactions.size() > 0) {
					List<Bank> banks = CacheManager.getInstance().getCache(AppUserBankCache.class)
							.getBanksForUserByEmail(user.getEmail());
					if (!banks.contains(bank)) {
						AppUserBank appUserBank = new AppUserBank();
						appUserBank.setUser(user);
						appUserBank.setBank(bank);
						appUserBankService.saveUserBank(appUserBank);
						CacheManager.getInstance().getCache(AppUserBankCache.class).updateBankToUser(user, bank);
						LOG.info("User Transactions Saved");
					}
					transactionService.saveUserTransactions(transactions, user, bank);
				} else {
					LOG.info("Transactions not found for user {} and bank {}", user.getEmail(), bank.getName());
				}
			}
		} else {
			LOG.info("Search Queries not found for bank {}", bank.getName());
		}
	}

	public UserTransactionThread(List<AppUser> users, IGmailClientService gmailClient,
			ITransactionService transactionService, IAppUserBankService appUserBankService,
			IAppUserService appUserService) {
		super();
		this.users = users;
		this.gmailClient = gmailClient;
		this.transactionService = transactionService;
		this.appUserBankService = appUserBankService;
		this.appUserService = appUserService;
	}

}
