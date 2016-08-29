package com.champ.services;

public interface IStartupService {

	public void loadContext();

	public void loadProperties();

	public void loadPaymentModeCache();

	public void loadBankCache();

	public void loadAppUserBanks();

	public void reloadContext();

	public void loadTransactionExecutor();

	public void loadSubmerchantsCache();

	public void loadCategories();

	public void loadSearchQueries();
}
