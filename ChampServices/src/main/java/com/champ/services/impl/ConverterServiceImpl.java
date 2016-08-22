package com.champ.services.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.base.dto.UserMappedTransaction;
import com.champ.base.response.UserBank;
import com.champ.base.response.UserTransaction;
import com.champ.core.cache.PaymentModeCache;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserLinkedAccount;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.core.entity.Category;
import com.champ.core.entity.SubMerchant;
import com.champ.core.utility.CacheManager;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.gmail.api.dto.TransactionDTO;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;
import com.champ.services.IAppUserService;
import com.champ.services.ICategoryService;
import com.champ.services.IConverterService;
import com.champ.services.ISubMerchantService;

@Service("converterService")
public class ConverterServiceImpl implements IConverterService {

	private SecureRandom secureRandom = new SecureRandom();

	@Autowired
	ISubMerchantService subMerchantService;

	@Autowired
	IAppUserService appUserService;

	@Autowired
	ICategoryService categoryService;

	public AppUserLinkedAccount getUserFromRequest(GmailTokensResponse request, UserInfoResponse userInfo, AppUser user, AppUserLinkedAccount linkedAccount) {
		if (linkedAccount == null) {
			linkedAccount = new AppUserLinkedAccount();
		}
		if (userInfo.getEmail() == null || userInfo.getEmail().equals("")) {
			return null;
		}
		linkedAccount.setEmail(userInfo.getEmail());
		linkedAccount.setAccessToken(request.getAccessToken());
		linkedAccount.setRefreshToken(request.getRefreshToken());
		linkedAccount.setName(userInfo.getName());
		linkedAccount.setImage(userInfo.getPicture());
		if (request.getExpires_in() != null) {
			linkedAccount.setGmailExpiryTime(DateUtils.addToDate(new Date(), TimeUnit.SECONDS, request.getExpires_in()));
		} else {
			linkedAccount.setGmailExpiryTime(new Date());
		}
		linkedAccount.setUser(user);
		return linkedAccount;
	}

	/**
	 * This works by choosing 130 bits from a cryptographically secure random
	 * bit generator, and encoding them in base-32. 128 bits is considered to be
	 * cryptographically strong, but each digit in a base 32 number can encode 5
	 * bits, so 128 is rounded up to the next multiple of 5. This encoding is
	 * compact and efficient, with 5 random bits per character. Compare this to
	 * a random UUID, which only has 3.4 bits per character in standard layout,
	 * and only 122 random bits in total.
	 **/
	public String generateTokenForUser() {
		return new BigInteger(130, secureRandom).toString(32);
	}

	public List<UserBank> getUserBankFromBanks(List<Bank> banks) {
		List<UserBank> userBanks = null;
		if (banks != null && banks.size() > 0) {
			userBanks = new ArrayList<UserBank>();
			for (Bank bank : banks) {
				UserBank userBank = new UserBank();
				userBank.setLogo(bank.getIconUrl());
				userBank.setName(bank.getName());
				userBank.setWebsite(bank.getWebsiteLink());
				userBanks.add(userBank);
			}
		}
		return userBanks;
	}

	@Transactional
	public List<UserTransaction> getUserTransactions(List<AppUserTransaction> transactions) {
		List<UserTransaction> userTransactions = new ArrayList<UserTransaction>();
		for (AppUserTransaction transaction : transactions) {
			UserTransaction userTransaction = new UserTransaction();
			userTransaction.setTransactionId(transaction.getId());
			userTransaction.setAmount(transaction.getAmount());
			userTransaction.setTransactionCode(transaction.getTransactionCode());
			if (transaction.getBank() != null) {
				userTransaction.setBankName(transaction.getBank().getName());
			}
			if (transaction.getSubMerchant() != null) {
				if (transaction.getSubMerchant().getMerchant() != null) {
					userTransaction.setMerchantName(transaction.getSubMerchant().getMerchant().getName());
					userTransaction.setMerchantIconUrl(transaction.getSubMerchant().getMerchant().getImageUrl());
					if (transaction.getSubMerchant().getMerchant().getCategory() != null) {
						userTransaction.setCategory(transaction.getSubMerchant().getMerchant().getCategory().getName());
					}
				} else {
					userTransaction.setMerchantName(transaction.getSubMerchant().getCode());
				}
			}
			if (transaction.getCategory() != null) {
				userTransaction.setCategory(transaction.getCategory().getName());
			}
			if (transaction.getUserDefined()) {
				userTransaction.setPaymentMode(transaction.getPaymentModeString());
			} else {
				PaymentModeCache cache = CacheManager.getInstance().getCache(PaymentModeCache.class);
				if (cache != null
						&& cache.getPaymentModeByExtractedString(transaction.getPaymentModeString()) != null) {
					userTransaction
							.setPaymentMode(cache.getPaymentModeByExtractedString(transaction.getPaymentModeString()));
				}
			}
			userTransaction.setTransactionDate(transaction.getTransactionDate());
			userTransactions.add(userTransaction);
		}
		return userTransactions;
	}

	public AppUserTransaction getTransactionFromDto(TransactionDTO dto, AppUser user, Bank bank) {
		AppUserTransaction transaction = new AppUserTransaction();
		transaction.setBank(bank);
		transaction.setUser(user);
		transaction.setPaymentModeString(dto.getPaymentModeString());
		SubMerchant subMerchant = subMerchantService.findSubMerchantByCode(dto.getSubMerchant());
		if (subMerchant == null) {
			subMerchant = new SubMerchant();
			subMerchant.setCode(dto.getSubMerchant());
			subMerchant.setMerchant(null);
			subMerchant = subMerchantService.saveOrUpdateSubMerchant(subMerchant);
		}
		transaction.setSubMerchant(subMerchant);
		transaction.setAmount(dto.getAmount());
		transaction.setPostTransactionBalance(dto.getBalance());
		transaction.setTransactionCode(dto.getTransactionCode());
		transaction.setTransactionDate(dto.getDate());
		return transaction;
	}

	public AppUserTransaction getTransactionFromDto(UserMappedTransaction transaction, String email) {
		AppUser user = appUserService.getUserByMobile(email);
		if (user == null) {
			return null;
		}
		AppUserTransaction newTransaction = new AppUserTransaction();
		newTransaction.setUser(user);
		newTransaction.setPaymentModeString(transaction.getPaymentMode());
		SubMerchant subMerchant = subMerchantService.findSubMerchantByCode(transaction.getSubmerchantCode());
		if (subMerchant == null) {
			subMerchant = new SubMerchant();
			subMerchant.setCode(transaction.getSubmerchantCode());
			subMerchant.setMerchant(null);
			subMerchant.setUserDefined(true);
			subMerchant = subMerchantService.saveOrUpdateSubMerchant(subMerchant);
		}
		newTransaction.setSubMerchant(subMerchant);
		newTransaction.setAmount(transaction.getAmount());
		Category category = categoryService.findCategoryByName(transaction.getCategory());
		if (category == null) {
			category = new Category();
			category.setName(transaction.getCategory());
			category.setColor(transaction.getCategoryColor());
			category.setUserDefined(true);
			category = categoryService.saveOrUpdateCategory(category);
		}
		newTransaction.setCategory(category);
		newTransaction.setTransactionDate(transaction.getTransactionDate());
		newTransaction.setUserDefined(true);
		return newTransaction;
	}

}
