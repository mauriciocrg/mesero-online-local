package com.mesero.web.layout;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.mesero.config.Config;
import com.mesero.web.table.ImpresorasTable;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ImpresoraVerticalLayout extends VerticalLayout {
	
	private HorizontalLayout headerLayout = null;
	private HorizontalLayout buttonsLayout = null;
	private FormLayout detailsLayout = null;
	
	private TextField portTextField = null;
	private TextField serverIPTextField = null;
	private TextField localIPTextField = null;
	
	private Button downloadButton = null;
	
	private Label label;
	
	private ImpresorasTable impresorasTable; 
	
	public ImpresoraVerticalLayout() {
		setCaption("Impresora");
		setIcon(FontAwesome.PRINT);
		setSpacing(true);
		setMargin(true);
		setWidth(100, Sizeable.Unit.PERCENTAGE);
		setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		impresorasTable = new ImpresorasTable();
		
		Panel tablePanel = new Panel();
	    tablePanel.setSizeFull();
	    tablePanel.setContent(impresorasTable);
	    
	    addComponents(getDetailsLayout(),tablePanel);
        
        setExpandRatio(tablePanel, 1.0f);
        
        Thread thread = new Thread() {
    		public void run() {
    			while(true) {
    				
    				impresorasTable.refresh();
    				
    				try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
    		}
    	};
    	thread.start();
	}

	//----- LAYOUTS ----------------------------------------------------------------------------------------------------------------
	
	private FormLayout getDetailsLayout() {
		if(detailsLayout == null) {
			detailsLayout = new FormLayout();
			detailsLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			detailsLayout.addComponents(getLabel(),getPortTextField(),getServerIpTextField(),getLocalIpTextField(),getDownloadButton());
		}
		return detailsLayout;
	}
	
	//----- FIELDS ----------------------------------------------------------------------------------------------------------------
	
	private TextField getPortTextField() {
		if(portTextField == null) {
			portTextField = new TextField("Puerto de acceso al Servidor de Impreción:");
			portTextField.setValue(Config.getInstance().getProperti(Config.PRINT_SERVER_PORT));
			portTextField.setReadOnly(true);
		}
		return portTextField;
	}
	
	private TextField getServerIpTextField() {
		if(serverIPTextField == null) {
			serverIPTextField = new TextField("IP del Servidor de Impreción:");
			serverIPTextField.setValue(Config.getInstance().getServerIP());
			serverIPTextField.setReadOnly(true);
		}
		return serverIPTextField;
	}
	
	private TextField getLocalIpTextField() {
		if(localIPTextField == null) {
			localIPTextField = new TextField("IP Local:");
			localIPTextField.setValue(getUI().getCurrent().getPage().getWebBrowser().getAddress());
			localIPTextField.setReadOnly(true);
		}
		return localIPTextField;
	}
	
	private Label getLabel() {
		if(label == null) {
			label = new Label("Detalles del Servidor de Impreción");
			label.setStyleName("h2");
		}
		return label;
	}
	
	//----- BUTTONS ----------------------------------------------------------------------------------------------------------------

	private Button getDownloadButton() {
		if(downloadButton == null) {
			downloadButton = new Button("Cliente de Impresión");
			downloadButton.setIcon(FontAwesome.DOWNLOAD);
	           
	        FileDownloader fileDownloader = new FileDownloader(new FileResource(new File(Config.getInstance().printClientFileName)));
	        fileDownloader.extend(downloadButton);
	        /*
			downloadButton.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		impresorasTable.refresh();
				}
		    });*/
		}
		return downloadButton;
	}
	
}
