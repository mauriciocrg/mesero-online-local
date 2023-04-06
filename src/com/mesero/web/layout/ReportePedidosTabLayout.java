package com.mesero.web.layout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


import com.mesero.web.pdf.ReportePedidosDeliveryPDF;
import com.mesero.web.table.ReportePedidosTable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ReportePedidosTabLayout extends VerticalLayout {
	
	private HorizontalLayout headerLayout = null;
	private HorizontalLayout buttonsLayout = null;
	private Panel tablePanel = new Panel();
	
	private Button actualizarButton = null;
	private Button pdfButton = null;
	private Button printButton = null;
	
	private CheckBox ultimosDosDiasCheck = null;
	
	private ReportePedidosTable reportePedidosTable = new ReportePedidosTable();
	
	public ReportePedidosTabLayout() {
		setCaption("Reporte de Pedidos (Delivery)");
		setSpacing(true);		
		setMargin(new MarginInfo(true,true,true,true));
		setSizeFull();
	
	    tablePanel.setSizeFull();
	    tablePanel.setContent(reportePedidosTable);
	    
	    addComponent(getHeaderLayout());
	    addComponent(tablePanel);
	    setExpandRatio(tablePanel, 1.0f);
	}
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.setSpacing(true);
	        
	    	headerLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
	    
	        headerLayout.addComponent(getUltimosDosDiasCheck());
	        headerLayout.addComponent(getButtonsLayout());
	        
	        headerLayout.setComponentAlignment(getUltimosDosDiasCheck(), Alignment.MIDDLE_LEFT);
	        headerLayout.setComponentAlignment(getButtonsLayout(), Alignment.MIDDLE_RIGHT);   
		}
		return headerLayout;
	}

	private HorizontalLayout getButtonsLayout() {
		if(buttonsLayout == null) {
			buttonsLayout = new HorizontalLayout();
			buttonsLayout.setSpacing(true);
	        
	        buttonsLayout.addComponent(getPdfButton());
	        //buttonsLayout.addComponent(getPrintButton());
	        buttonsLayout.addComponent(getActualizarButton());
	        
	        
	        buttonsLayout.setComponentAlignment(getPdfButton(), Alignment.MIDDLE_RIGHT);
	        //buttonsLayout.setComponentAlignment(getPrintButton(), Alignment.MIDDLE_RIGHT);
	        buttonsLayout.setComponentAlignment(getActualizarButton(), Alignment.MIDDLE_RIGHT);   
		}
		return buttonsLayout;
	}
	
	private Button getActualizarButton() {
		if(actualizarButton == null) {
			actualizarButton = new Button();
		    actualizarButton.setIcon(FontAwesome.REFRESH);
		    actualizarButton.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		reportePedidosTable.refresh(getUltimosDosDiasCheck().getValue());
				}
		    });
		}
		return actualizarButton;
	}
	
	private Button getPrintButton() {
		//if(printButton == null) {
			Button button = new Button();
		    button.setIcon(FontAwesome.PRINT);
		    //printButton.setEnabled(false);
		//}
		return button;
	}
	
	
	private Button getPdfButton() {
		if(pdfButton == null) {
			pdfButton = new Button();
		    pdfButton.setIcon(FontAwesome.FILE_PDF_O);
		    pdfButton.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		//reportePedidosTable.refresh(getUltimosDosDiasCheck().getValue());
		    		ReportePedidosDeliveryPDF reportePedidosDeliveryPDF = new ReportePedidosDeliveryPDF(reportePedidosTable);
		    		final File file = reportePedidosDeliveryPDF.getFile();
		    		
		    		//System.out.println(file.getAbsolutePath());
		    		
		    		StreamSource source = new StreamSource() {
		        		public InputStream getStream() {
		        			try {
		    					return  new FileInputStream(file.getAbsoluteFile());
		    				} catch (FileNotFoundException e) {
		    					// TODO Auto-generated catch block
		    					e.printStackTrace();
		    				}
		    				return null;
		        		}	
		        	};
		    		
		    		StreamResource resource = new StreamResource(source,file.getName());
		    		
		    		resource.setMIMEType("application/pdf");
		            resource.getStream().setParameter("Content-Disposition", "attachment; filename="+file.getName());

		            BrowserWindowOpener opener = new BrowserWindowOpener(resource);
		            if(getButtonsLayout().getComponent(1) == printButton) getButtonsLayout().removeComponent(printButton);
		            printButton = getPrintButton();
		            opener.extend(printButton);
		            //getPrintButton().setEnabled(true);
		            getButtonsLayout().addComponent(printButton,1);
				}
		    });
		}
		return pdfButton;
	}
	
	private CheckBox getUltimosDosDiasCheck() {
		if(ultimosDosDiasCheck == null) {
			ultimosDosDiasCheck = new CheckBox("Últimos dos Días");
			ultimosDosDiasCheck.setValue(true);
			ultimosDosDiasCheck.addValueChangeListener(new ValueChangeListener(){
				@Override
				public void valueChange(ValueChangeEvent event) {
					reportePedidosTable.refresh(getUltimosDosDiasCheck().getValue());
				}
			});
		}
		return ultimosDosDiasCheck;
	}
}
