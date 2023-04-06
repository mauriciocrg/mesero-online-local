package com.mesero.mobil.layout;

import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.mobil.component.IconButtonMobil;
import com.mesero.mobil.component.LabelMobil;
import com.mesero.mobil.window.MenuItemMobilWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class PedidoItemLayoutMobil extends HorizontalLayout {

	private IconButtonMobil selectButton;
	private IconButtonMobil removeButton;
	private LabelMobil nombreLabel;
	private PedidoItem pedidoItem;
	private Pedido pedido;
	private int fontSize;
	private int buttonSize;
	private int width;
	private int height;
	
	private VerticalLayout pedidoLayout;
	
	public PedidoItemLayoutMobil(VerticalLayout pedidoLayout, 
			Pedido pedido, 
			PedidoItem pedidoItem, 
			int width, 
			int height, 
			int fontSize,
			int buttonSize) {
		this.pedidoLayout = pedidoLayout;
		this.pedidoItem = pedidoItem;
		this.pedido = pedido;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize-10;
		this.buttonSize = buttonSize-10;
	
		removeAllComponents();
		
		setHeight(this.buttonSize,Sizeable.Unit.PIXELS);
		setWidth(100,Sizeable.Unit.PERCENTAGE);
		setSpacing(false);
		
		addComponents(getNombreLabel(), getRemoveButton(), getSelectButton());
		setComponentAlignment(getNombreLabel(), Alignment.MIDDLE_LEFT);
		setComponentAlignment(getRemoveButton(), Alignment.MIDDLE_RIGHT);
		setComponentAlignment(getSelectButton(), Alignment.MIDDLE_RIGHT);
		setExpandRatio(getRemoveButton(), 0);
		setExpandRatio(getSelectButton(), 0);
		setExpandRatio(getNombreLabel(), 1);
		
		setStyleName("v-border", true);
		
	}
	
	private IconButtonMobil getSelectButton() {
		if(selectButton == null) {
			selectButton = new IconButtonMobil(FontAwesome.CHEVRON_RIGHT, fontSize, buttonSize-5);
			selectButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					MenuItemMobilWindow menuItemMobilWindow = new MenuItemMobilWindow(pedidoItem, width, height, fontSize, buttonSize);
					menuItemMobilWindow.addCloseListener(new CloseListener() {
						@Override
						public void windowClose(CloseEvent e) {
							getNombreLabel().update(pedidoItem.getMenuItem().getNombre_menuItem()+" ("+pedidoItem.getCantidad()+")", fontSize);
						}
					});
					getUI().addWindow(menuItemMobilWindow);
				}
			});
		}
		return selectButton;
	}
	
	private IconButtonMobil getRemoveButton() {
		if(removeButton == null) {
			removeButton = new IconButtonMobil(FontAwesome.REMOVE, fontSize, buttonSize-5);
			removeButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					pedido.getItems().remove(pedidoItem);
					pedidoLayout.removeComponent(getThis());
				}
			});
		}
		return removeButton;
	}
	
	private LabelMobil getNombreLabel() {
		if(nombreLabel == null) {
			nombreLabel = new LabelMobil(pedidoItem.getMenuItem().getNombre_menuItem()+" ("+pedidoItem.getCantidad()+")", fontSize);
		}
		return nombreLabel;
	}
	
	private HorizontalLayout getThis() {
		return this;
	}
}
