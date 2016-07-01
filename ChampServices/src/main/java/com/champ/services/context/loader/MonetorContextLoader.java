package com.champ.services.context.loader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.champ.services.IStartupService;

public class MonetorContextLoader implements ServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(MonetorContextLoader.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.info("Context Destroyed");
	}

	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("Context Initialization Event Called");
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		final IStartupService startupService = context.getBean(IStartupService.class);
		try {
			LOG.info("Loading Application Context");
			startupService.loadContext();
		} catch (Exception e) {
			LOG.error("Error while initializing application:", e);
		}

	}

}
