package com.mesero.web.layout;

import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.web.window.MenuItemPedidoWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;

public class MenuItemLayout extends HorizontalLayout {

	private Button selectButton;
	private Label nombreLabel;
	private Pedido pedido;
	private MenuItem menuItem;
	
	
	public MenuItemLayout(Pedido pedido, MenuItem menuItem) {
		this.pedido = pedido;
		this.menuItem = menuItem;
	
		
		removeAllComponents();
		
		//setHeight(this.buttonSize,Sizeable.Unit.PIXELS);
		setWidth(100,Sizeable.Unit.PERCENTAGE);
		setSpacing(true);
		
		addComponents(getNombreLabel(), getSelectButton());
		setComponentAlignment(getNombreLabel(), Alignment.MIDDLE_LEFT);
		setComponentAlignment(getSelectButton(), Alignment.MIDDLE_RIGHT);
		
		setStyleName("v-border", true);
		
	}
	
	private Button getSelectButton() {
		if(selectButton == null) {
			selectButton = new Button(FontAwesome.CHEVRON_RIGHT);
			selectButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new MenuItemPedidoWindow(pedido, menuItem));
				}
			});
		}
		return selectButton;
	}
	
	private Label getNombreLabel() {
		if(nombreLabel == null) {
			nombreLabel = new Label(menuItem.getNombre_menuItem());
		}
		return nombreLabel;
	}
}
