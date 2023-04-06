package com.mesero.web.layout;


import com.vaadin.server.Sizeable;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class FacturacionVerticalLayout extends VerticalLayout {

	private FacturacionDiariaVerticalLayout facturacionDiariaVerticalLayout = new FacturacionDiariaVerticalLayout();
	private FacturacionProductosVerticalLayout facturacionProductiosVerticalLayout = new FacturacionProductosVerticalLayout();
	private FacturacionGraficasVerticalLayout facturacionGraficasVerticalLayout = new FacturacionGraficasVerticalLayout();
	
	public FacturacionVerticalLayout() {
		removeAllComponents();
		setSpacing(true);
		setMargin(true);
		
		setSizeFull();
		
		TabSheet tabsheet = new TabSheet();
		
		tabsheet.setWidth(100, Sizeable.Unit.PERCENTAGE);
		tabsheet.setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		tabsheet.addComponent(facturacionDiariaVerticalLayout);
		tabsheet.addComponent(facturacionProductiosVerticalLayout);
		tabsheet.addComponent(facturacionGraficasVerticalLayout);
		
		addComponent(tabsheet);
	}
	/*
	public PedidosTabLayout getPedidosTabLayout() {
		return this.usuarioVerticalLayout;
	}*/
	
}
