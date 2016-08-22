package com.champ.services.thread;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.champ.core.cache.BankCache;
import com.champ.core.dto.SearchQueryParserDto;
import com.champ.core.entity.AppUserLinkedAccount;
import com.champ.core.entity.Bank;
import com.champ.core.utility.CacheManager;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.services.IAppUserLinkedAccountService;
import com.champ.services.IGmailClientService;

public class UserTransactionThread implements Runnable {

	private List<AppUserLinkedAccount> linkedAccounts;
	private IGmailClientService gmailClient;
	private IAppUserLinkedAccountService appUserLinkedAccountService;

	private static final Logger LOG = LoggerFactory.getLogger(UserTransactionThread.class);

	public void run() {
		try {
			LOG.info("Thread to get data from gmail called");
			if (!CollectionUtils.isEmpty(linkedAccounts)) {
				List<Bank> banks = CacheManager.getInstance().getCache(BankCache.class).getAllBanks();
				LOG.info("Banks found from Cache {}", banks);
				for (AppUserLinkedAccount account : this.linkedAccounts) {
					try {
						if (banks != null && banks.size() > 0) {
							for (Bank bank : banks) {
								getAndSaveUserTransactions(account, bank);
							}
						} else {
							LOG.info("Banks not found for user {}", account.getEmail());
						}
						account.setLastSyncedOn(DateUtils.addToDate(new Date(), TimeUnit.SECONDS, -10));
						account.setSynced(true);
						appUserLinkedAccountService.saveOrUpdateLinkedAccount(account);
					} catch (Exception e) {
						LOG.error("Error while getting transactions for user {}", account.getEmail(), e);
					}
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

	public void getAndSaveUserTransactions(AppUserLinkedAccount account, Bank bank) throws Exception {
		List<SearchQueryParserDto> searchQueries = CacheManager.getInstance().getCache(BankCache.class)
				.getSearchQueryParserByBankName(bank.getName());
		if (searchQueries != null && searchQueries.size() > 0) {
			for (SearchQueryParserDto dto : searchQueries) {
				gmailClient.getMessages(account, dto.getSearchQuery(), dto.getParser(), bank);
				LOG.info("All transactions saved");
			}
		} else {
			LOG.info("Search Queries not found for bank {}", bank.getName());
		}
	}

	public UserTransactionThread(List<AppUserLinkedAccount> linkedAccounts, IGmailClientService gmailClient,
			IAppUserLinkedAccountService appUserLinkedAccountService) {
		super();
		this.linkedAccounts = linkedAccounts;
		this.gmailClient = gmailClient;
		this.appUserLinkedAccountService = appUserLinkedAccountService;
	}

}
