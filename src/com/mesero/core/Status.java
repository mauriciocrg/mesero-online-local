package com.mesero.core;

import org.jsoup.Jsoup;

import com.mesero.config.Config;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;



public class Status {

	public static String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	
	private static Status status = null;
	
	private Status() {}
	
	public String getIconAsHtml(String caption,FontAwesome fontAwesome,int fontSize) {

		org.jsoup.nodes.Document doc = Jsoup.parse(fontAwesome.getHtml(),"ISO-8859-1");
        
		org.jsoup.nodes.Element element = doc.getElementsByClass("v-icon").get(0);
        
        element = element.attr("style", element.attr("style")+"font-size:"+fontSize+"px;text-align:center;");
        
        element.appendText(caption);
        
        return element.outerHtml();
	}
	
	public void incrementarContador() {
		Integer contador = new Integer(Config.getInstance().getProperti(Config.CONTADOR));
		contador++;
		Config.getInstance().setProperti(Config.CONTADOR,contador.toString());
	}
	
	public static Status getInstance() {
		if(status == null) {
			status = new Status();
		}
		return status;
	}
}
