package com.mesero.mobil.layout;

import com.mesero.bean.Menu;
import com.mesero.bean.MenuItem;
import com.mesero.mobil.component.LabelMobil;
//import com.mesero_online_local.mobil.component.LabelMobil;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;

public class MenuLayoutMobil extends HorizontalLayout {

	private LabelMobil nombreLabel;
	private Menu menu;
	private int fontSize;
	private int buttonSize;
	
	
	public MenuLayoutMobil(Menu menu,int fontSize,int buttonSize) {
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
	
	private LabelMobil getNombreLabel() {
		if(nombreLabel == null) {
			nombreLabel = new LabelMobil(menu.getNombre_menu(), fontSize);
		}
		return nombreLabel;
	}
}
