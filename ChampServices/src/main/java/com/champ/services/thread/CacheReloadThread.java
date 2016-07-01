package com.champ.services.thread;

import com.champ.services.IStartupService;

public class CacheReloadThread implements Runnable {

	private IStartupService startupService;

	public CacheReloadThread(IStartupService startupService) {
		super();
		this.startupService = startupService;
	}

	public IStartupService getStartupService() {
		return startupService;
	}

	public void setStartupService(IStartupService startupService) {
		this.startupService = startupService;
	}

	public void run() {
		startupService.reloadContext();
	}

}
