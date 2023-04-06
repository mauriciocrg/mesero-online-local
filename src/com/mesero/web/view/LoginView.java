package com.mesero.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mesero.config.Config;
import com.mesero.config.Cripto;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class LoginView extends VerticalLayout implements View {

	protected static final String MAINVIEW = "mainView";
    
    private Navigator navigator = null;
	
    public LoginView(final Navigator navigator) {
        
    	this.navigator = navigator;

    	setSizeFull(); 
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.addStyleName("outlined");
        

        gridLayout.removeAllComponents();

        gridLayout.setWidth(50.0f, Unit.PERCENTAGE);
        gridLayout.setHeight(50.0f, Unit.PERCENTAGE);
        
        gridLayout.setRows(3);
        gridLayout.setColumns(3);

        CustomLayout customLayout = null;
                
        String filePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"loginTemplate.xml";
       
        try {
            customLayout = new CustomLayout(new FileInputStream(new File(filePath))); 
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
        final PasswordField passwordField = new PasswordField();
        passwordField.setWidth(100.0f, Unit.PERCENTAGE);
        customLayout.addComponent(passwordField, "password");
        
        final Label message = new Label("Mesero on-line (Local) 1.0");        
        customLayout.addComponent(message, "message");

        final Button ok = new Button("Login");
        ok.addClickListener(new ClickListener(){
            public void buttonClick(ClickEvent event) {
      
            	String password = passwordField.getValue();
            	
            	if(Cripto.Desencriptar(Config.getInstance().getProperti(Config.APP_PASSWORD)).equals(password)) {
            		navigator.navigateTo(MAINVIEW);
     			} else {
    				Notification.show("Contraseña incorrecta.", Type.TRAY_NOTIFICATION);
    			}
            }
        });

        customLayout.addComponent(ok, "okbutton");

        gridLayout.addComponent(customLayout, 1, 1);
        
        setWidth(100.0f, Unit.PERCENTAGE);
        setHeight(100.0f, Unit.PERCENTAGE);
        
        addComponent(gridLayout);
        setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		for(Window window : getUI().getWindows()) {
    		window.close();
    	}
	}
}
