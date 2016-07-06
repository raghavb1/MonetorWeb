package com.champ.services.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.champ.base.response.UserBank;
import com.champ.base.response.UserTransaction;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.entity.AppUser;
import com.champ.core.entity.AppUserTransaction;
import com.champ.core.entity.Bank;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.core.utility.DateUtils;
import com.champ.core.utility.DateUtils.TimeUnit;
import com.champ.gmail.api.response.GmailTokensResponse;
import com.champ.gmail.api.response.UserInfoResponse;
import com.champ.services.IConverterService;

@Service("converterService")
public class ConverterServiceImpl implements IConverterService {

	private SecureRandom secureRandom = new SecureRandom();

	public AppUser getUserFromRequest(GmailTokensResponse request, UserInfoResponse userInfo, AppUser user) {
		if (user == null) {
			user = new AppUser();
		}
		user.setEmail(userInfo.getEmail());
		user.setToken(generateTokenForUser());
		user.setAccessToken(request.getAccessToken());
		user.setRefreshToken(request.getRefreshToken());
		user.setTokenExpiryTime(DateUtils.addToDate(new Date(), TimeUnit.SECONDS, CacheManager.getInstance()
				.getCache(PropertyMapCache.class).getPropertyInteger(Property.TOKEN_EXPIRY_UPDATE_SECONDS)));
		if (request.getGmailTokenExpirySeconds() != null) {
			user.setGmailExpiryTime(
					DateUtils.addToDate(new Date(), TimeUnit.SECONDS, request.getGmailTokenExpirySeconds()));
		} else {
			user.setGmailExpiryTime(new Date());
		}
		return user;
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
			userTransaction.setAmount(transaction.getAmount());
			if (transaction.getBank() != null) {
				userTransaction.setBankName(transaction.getBank().getName());
			}
			if (transaction.getSubMerchant() != null) {
				if (transaction.getSubMerchant().getMerchant() != null) {
					userTransaction.setMerchantName(transaction.getSubMerchant().getMerchant().getName());
					if (transaction.getSubMerchant().getMerchant().getCategory() != null) {
						userTransaction.setCategory(transaction.getSubMerchant().getMerchant().getCategory().getName());
					}
				} else {
					userTransaction.setMerchantName(transaction.getSubMerchant().getCode());
				}
			}
			if (transaction.getBankPaymentMode() != null) {
				userTransaction.setPaymentMode(transaction.getBankPaymentMode().getPaymentMode());
			}
			userTransaction.setTransactionDate(transaction.getTransactionDate());
			userTransaction.setTransactionId(transaction.getTransactionId());
			userTransactions.add(userTransaction);
		}
		return userTransactions;
	}

}
