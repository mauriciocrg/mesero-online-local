package com.mesero.web.layout;


import com.vaadin.server.Sizeable;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class PedidosVerticalLayout extends VerticalLayout {

	private PedidosTabLayout pedidosTabLayout = new PedidosTabLayout();
	private ReportePedidosTabLayout reportePedidosTabLayout = new ReportePedidosTabLayout();
	
	public PedidosVerticalLayout() {
		removeAllComponents();
		setSpacing(true);
		setMargin(true);
		
		setSizeFull();
		
		TabSheet tabsheet = new TabSheet();
		
		tabsheet.setWidth(100, Sizeable.Unit.PERCENTAGE);
		tabsheet.setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		tabsheet.addComponent(pedidosTabLayout);
		tabsheet.addComponent(reportePedidosTabLayout);
		
		addComponent(tabsheet);
	}
	
	public PedidosTabLayout getPedidosTabLayout() {
		return this.pedidosTabLayout;
	}
	
}
