package com.mesero.web.layout;


import com.mesero.web.layout.ImpresoraVerticalLayout;
import com.mesero.web.layout.UsuarioVerticalLayout;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ConfigVerticalLayout extends VerticalLayout {

	private UsuarioVerticalLayout usuarioVerticalLayout = new UsuarioVerticalLayout();
	private ImpresoraVerticalLayout impresoraVerticalLayout = new ImpresoraVerticalLayout();
	
	public ConfigVerticalLayout() {
		removeAllComponents();
		setSpacing(true);
		setMargin(true);
		
		setSizeFull();
		
		TabSheet tabsheet = new TabSheet();
		
		tabsheet.setWidth(100, Sizeable.Unit.PERCENTAGE);
		tabsheet.setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		tabsheet.addComponent(usuarioVerticalLayout);
		tabsheet.addComponent(impresoraVerticalLayout);
		
		addComponent(tabsheet);
	}
	/*
	public PedidosTabLayout getPedidosTabLayout() {
		return this.usuarioVerticalLayout;
	}*/
	
}
