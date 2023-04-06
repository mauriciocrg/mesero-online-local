package com.mesero.core;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mesero.config.Config;

public class ContextListener implements ServletContextListener {

	private ServletContext context = null;

	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		Config.baseDirectory = context.getRealPath("");
		Config.getInstance();
		PrintServer.getInstance();
	}
	
	public void contextDestroyed(ServletContextEvent event) {
	}
}

