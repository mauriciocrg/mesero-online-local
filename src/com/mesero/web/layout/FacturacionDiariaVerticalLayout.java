package com.mesero.web.layout;

import com.mesero.web.table.FacturacionDiariaTable;
/*import com.mesero_online_local.manageBean.ManagePedido;
import com.mesero_online_local.web.table.FacturacionDiariaTable;*/
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class FacturacionDiariaVerticalLayout extends VerticalLayout {

	private HorizontalLayout headerLayout = null;
	private HorizontalLayout buttonsLayout = null;
	private HorizontalLayout fechasLayout;
	private FormLayout searchLayout;
	
	private DateField fechaDesdeDateField;
	private DateField fechaHastaDateField;
	
	private Label label;
	
	private Button actualizarButton = null;
	
	private FacturacionDiariaTable facturacionTable = new FacturacionDiariaTable();
	
	public FacturacionDiariaVerticalLayout() {
		setCaption("Por Día");
		setIcon(FontAwesome.MONEY);
		removeAllComponents();
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		
		Panel tablePanel = new Panel();
	    tablePanel.setSizeFull();
	    tablePanel.setContent(facturacionTable);
	    
	    addComponent(getHeaderLayout());
        
        addComponent(tablePanel);
        setExpandRatio(tablePanel, 1.0f);
	}
	
	//----- LAYOUTS ----------------------------------------------------------------------------------------------------------------
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.setSpacing(true);
	        
	    	headerLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
	    	
	        headerLayout.addComponents(getSearchLayout(),getButtonsLayout());
	        headerLayout.setComponentAlignment(getSearchLayout(), Alignment.TOP_CENTER);
	        headerLayout.setComponentAlignment(getButtonsLayout(), Alignment.TOP_RIGHT);
	        
	        headerLayout.setExpandRatio(getSearchLayout(), 1.0f);
	    }
		return headerLayout;
	}
	
	private HorizontalLayout getButtonsLayout() {
		if(buttonsLayout == null) {
			buttonsLayout = new HorizontalLayout();
			buttonsLayout.setMargin(false);
			buttonsLayout.setSpacing(true);
			buttonsLayout.addComponents(getActualizarButton());
		}
		return buttonsLayout;
	}
	
	private FormLayout getSearchLayout() {
		if(searchLayout == null) {
			searchLayout = new FormLayout();
			searchLayout.setMargin(false);
			searchLayout.setSpacing(true);
			searchLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			searchLayout.addComponents(getLabel(),getFechasLayout());
		}
		return searchLayout;
	}
	
	private HorizontalLayout getFechasLayout() {
		if(fechasLayout == null) {
			fechasLayout = new HorizontalLayout();
			fechasLayout.setSpacing(true);
			fechasLayout.addComponents(getFechaDesdeDateField(),getFechaHastaDateField());
			fechasLayout.setCaption("Lapso de Fechas:");
		}
		return fechasLayout;
	}
	
	//----Fields-----------------------------------------------------------------------------------
	
	private DateField getFechaDesdeDateField() {
		if(fechaDesdeDateField == null) {
			fechaDesdeDateField = new DateField();
			fechaDesdeDateField.addValueChangeListener(new ValueChangeListener(){
				public void valueChange(ValueChangeEvent event) {
					facturacionTable.refresh(
							   getFechaDesdeDateField().getValue(), 
							   getFechaHastaDateField().getValue());
				}
				
			});
		}
		return fechaDesdeDateField;
	}
	
	private DateField getFechaHastaDateField() {
		if(fechaHastaDateField == null) {
			fechaHastaDateField = new DateField();
			fechaHastaDateField.addValueChangeListener(new ValueChangeListener(){
				public void valueChange(ValueChangeEvent event) {
					facturacionTable.refresh(
							   getFechaDesdeDateField().getValue(), 
							   getFechaHastaDateField().getValue());
				}
				
			});
		}
		return fechaHastaDateField;
	}
	
	private Label getLabel() {
		if(label == null) {
			label = new Label("Facturación por día");
			label.setStyleName("h2");
		}
		return label;
	}
	
	//----- BUTTONS ----------------------------------------------------------------------------------------------------------------

	private Button getActualizarButton() {
		if(actualizarButton == null) {
			actualizarButton = new Button();
		    actualizarButton.setIcon(FontAwesome.REFRESH);
		    actualizarButton.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		facturacionTable.refresh(
							   getFechaDesdeDateField().getValue(), 
							   getFechaHastaDateField().getValue());
				}
		    });
		}
		return actualizarButton;
	}
}
