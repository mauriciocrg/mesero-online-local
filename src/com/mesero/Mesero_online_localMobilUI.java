package com.mesero;


import com.mesero.mobil.view.MainMobilView;
import com.mesero.mobil.view.StartMobilView;
import com.mesero.mobil.window.ConfirmarPedidoMobilWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Theme("mesero_online_local")
@Widgetset("com.mesero_online_local.Html5Widgetset")
public class Mesero_online_localMobilUI extends UI {
	
	private Navigator navigator;
	
	@Override
	protected void init(VaadinRequest request) {
		

    	navigator = new Navigator(this, this);
    	
    	final MainMobilView mainMobilView = new MainMobilView(navigator);
    	final StartMobilView startMobilView = new StartMobilView(navigator,mainMobilView);
    	
    	navigator.addView("main", mainMobilView);
    	navigator.addView("start", startMobilView);
    	
        
        navigator.navigateTo("start");
        
        //<meta name="viewport" content="width=device-width" />
        
        getPage().getJavaScript().execute("document.head.innerHTML += '<meta name=\"viewport\" content=\"initial-scale = 1.0,maximum-scale = 1.0\">'");
        
        getPage().addBrowserWindowResizeListener(new BrowserWindowResizeListener(){
			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				// TODO Auto-generated method stub
				//System.out.println("new WIDTH = "+event.getWidth()+" new HEIGHT = "+event.getHeight());

				for(Window window :getUI().getWindows()) {
					if(!(window instanceof ConfirmarPedidoMobilWindow)) window.close();
				}
				
				mainMobilView.updateComponents(event.getWidth(), event.getHeight());
				startMobilView.updateComponents(event.getWidth(), event.getHeight());	
			}        	
        });
	}

}
