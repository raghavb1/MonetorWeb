package com.champ.services;

public interface IStartupService {

	public void loadContext();

	public void loadProperties();

	public void loadPaymentModeCache();
	
	public void loadAppUserBanks();
	
	public void reloadContext();
}
