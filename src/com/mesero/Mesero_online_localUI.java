package com.mesero;


import com.mesero.web.view.LoginView;
import com.mesero.web.view.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mesero_online_local")
@Widgetset("com.mesero_online_local.Html5Widgetset")
public class Mesero_online_localUI extends UI {

	private Navigator navigator;
	
	@Override
	protected void init(VaadinRequest request) {
		
		//HibernateConfiguration.getInstance();
		
		//System.out.println("ip=  "+request.getRemoteAddr());
		/*
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
		
		if (ip == null) {
		    ip = request.getRemoteAddr();
		}
		System.out.println("ipAddress:" + ip);
		*/
		setPollInterval(1000);
		
		navigator = new Navigator(this, this);
		
		navigator.addView("login", new LoginView(navigator));
		navigator.addView("mainView", new MainView(navigator));
        
        navigator.navigateTo("login");
	}

}