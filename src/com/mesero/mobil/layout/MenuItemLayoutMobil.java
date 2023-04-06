package com.mesero.mobil.layout;


import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.mobil.component.IconButtonMobil;
import com.mesero.mobil.component.LabelMobil;
import com.mesero.mobil.window.MenuItemMobilWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;

public class MenuItemLayoutMobil extends HorizontalLayout {
	
	private IconButtonMobil selectButton;
	private LabelMobil nombreLabel;
	private Pedido pedido;
	private MenuItem menuItem;
	private int fontSize;
	private int buttonSize;
	private int width;
	private int height;
	
	
	public MenuItemLayoutMobil(Pedido pedido, MenuItem menuItem, int width, int height, int fontSize,int buttonSize) {
		this.pedido = pedido;
		this.menuItem = menuItem;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize-10;
		this.buttonSize = buttonSize-10;
	
		
		removeAllComponents();
		
		setHeight(this.buttonSize,Sizeable.Unit.PIXELS);
		setWidth(100,Sizeable.Unit.PERCENTAGE);
		setSpacing(true);
		
		addComponents(getNombreLabel(), getSelectButton());
		setComponentAlignment(getNombreLabel(), Alignment.MIDDLE_LEFT);
		setComponentAlignment(getSelectButton(), Alignment.MIDDLE_RIGHT);
		
		setStyleName("v-border", true);
		
	}
	
	private IconButtonMobil getSelectButton() {
		if(selectButton == null) {
			selectButton = new IconButtonMobil(FontAwesome.CHEVRON_RIGHT, fontSize, buttonSize-5);
			selectButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new MenuItemMobilWindow(pedido, menuItem, width, height, fontSize, buttonSize));
				}
			});
		}
		return selectButton;
	}
	
	private LabelMobil getNombreLabel() {
		if(nombreLabel == null) {
			nombreLabel = new LabelMobil(menuItem.getNombre_menuItem(), fontSize);
		}
		return nombreLabel;
	}
}
