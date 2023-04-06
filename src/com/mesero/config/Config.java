package com.mesero.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;


public class Config {
	
	public static String MYSQL_PATH = "mysql_path";
	public static String USER = "user";
	public static String PASSWORD = "password";
	public static String APP_PASSWORD = "app-password";
	public static String CONTADOR = "contador";
	public static String PRINT_SERVER_PORT = "print-server-port";


	private static Config config = null;
	
	public static String baseDirectory = "";
	
	public String printClientFileName = baseDirectory+File.separator+"config"+File.separator+"PrintClient.zip";
	
	//VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
	
	private Properties properties = new Properties();
	
	private String propertiesFileName = baseDirectory+File.separator+"config"+File.separator+"config.properties";
	
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	
	private Config() {
		try {
			
			this.inputStream = new FileInputStream(new File(this.propertiesFileName));
			this.properties.load(inputStream);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getProperti(String properti) {
		return this.properties.getProperty(properti);
	}
	
	public void setProperti(String properti, String value) {
		try {
			this.outputStream = new FileOutputStream(new File(this.propertiesFileName));
			this.properties.setProperty(properti, value);
			this.properties.store(this.outputStream, "");
			this.outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getServerIP() {
		
		String hostAddress = "";
		
		try {
			
			Socket s = new Socket("www.google.com", 80);
			InetAddress ip = s.getLocalAddress();
			s.close();
			
			hostAddress = ip.getHostAddress();		
			
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hostAddress;
	}
	
	public String getClientIP() {
		
		String ip = Page.getCurrent().getWebBrowser().getAddress();
		if(ip.equals("127.0.0.1")) ip = Config.getInstance().getServerIP();
		
		return ip;
	}
	
	public static Config getInstance() {
		if(config == null) {
			config = new Config();
		}
		return config;
	}
}
