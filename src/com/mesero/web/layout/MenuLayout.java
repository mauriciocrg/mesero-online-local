package com.mesero.web.layout;

import com.mesero.bean.Menu;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class MenuLayout extends HorizontalLayout {
	
	private Label nombreLabel;
	private Menu menu;
	private int fontSize;
	private int buttonSize;
	
	
	public MenuLayout(Menu menu) {
		this.menu = menu;
		this.fontSize = fontSize-10;
		this.buttonSize = buttonSize-5;
	
		
		removeAllComponents();
		
		setHeight(this.buttonSize,Sizeable.Unit.PIXELS);
		setWidth(100,Sizeable.Unit.PERCENTAGE);
		setSpacing(true);
		setStyleName("v-fondo", true);
		setStyleName("v-border", true);
		
		addComponents(getNombreLabel());
		setComponentAlignment(getNombreLabel(), Alignment.MIDDLE_LEFT);
	}
	
	private Label getNombreLabel() {
		if(nombreLabel == null) {
			nombreLabel = new Label(menu.getNombre_menu());
		}
		return nombreLabel;
	}

}
