package com.mesero;

import com.vaadin.annotations.Theme;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mesero_online")
public class MeseroOnLineLocalProvider extends UIProvider {

	@Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        // could also use browser version etc.
    	
    	//System.out.println(event.getRequest().getHeader("user-agent"));
    	
        if (event.getRequest().getHeader("user-agent").toLowerCase().contains("mobile") || 
        	event.getRequest().getHeader("user-agent").toLowerCase().contains("android")) {
            return Mesero_online_localMobilUI.class;
        } else {
            return Mesero_online_localUI.class;
        }
    }
}
