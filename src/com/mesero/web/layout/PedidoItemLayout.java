package com.mesero.web.layout;

import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.web.window.MenuItemPedidoWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class PedidoItemLayout extends HorizontalLayout {

	private Button selectButton;
	private Button removeButton;
	private Label nombreLabel;
	private PedidoItem pedidoItem;
	private Pedido pedido;
	
	private VerticalLayout pedidoLayout;
	
	public PedidoItemLayout(VerticalLayout pedidoLayout, 
			Pedido pedido, 
			PedidoItem pedidoItem) {
		this.pedidoLayout = pedidoLayout;
		this.pedidoItem = pedidoItem;
		this.pedido = pedido;
	
		removeAllComponents();
		
		//setHeight(this.buttonSize,Sizeable.Unit.PIXELS);
		setWidth(100,Sizeable.Unit.PERCENTAGE);
		setSpacing(true);
		
		addComponents(getNombreLabel(), getRemoveButton(), getSelectButton());
		setComponentAlignment(getNombreLabel(), Alignment.MIDDLE_LEFT);
		setComponentAlignment(getRemoveButton(), Alignment.MIDDLE_RIGHT);
		setComponentAlignment(getSelectButton(), Alignment.MIDDLE_RIGHT);
		setExpandRatio(getRemoveButton(), 0);
		setExpandRatio(getSelectButton(), 0);
		setExpandRatio(getNombreLabel(), 1);
		
		setStyleName("v-border", true);
		
	}
	
	private Button getSelectButton() {
		if(selectButton == null) {
			selectButton = new Button(FontAwesome.CHEVRON_RIGHT);
			selectButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					MenuItemPedidoWindow menuItemWindow = new MenuItemPedidoWindow(pedidoItem);
					menuItemWindow.addCloseListener(new CloseListener() {
						@Override
						public void windowClose(CloseEvent e) {
							getNombreLabel().setValue(pedidoItem.getMenuItem().getNombre_menuItem()+" ("+pedidoItem.getCantidad()+")");
						}
					});
					getUI().addWindow(menuItemWindow);
				}
			});
		}
		return selectButton;
	}
	
	private Button getRemoveButton() {
		if(removeButton == null) {
			removeButton = new Button(FontAwesome.REMOVE);
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
	
	private Label getNombreLabel() {
		if(nombreLabel == null) {
			nombreLabel = new Label(pedidoItem.getMenuItem().getNombre_menuItem()+" ("+pedidoItem.getCantidad()+")");
		}
		return nombreLabel;
	}
	
	private HorizontalLayout getThis() {
		return this;
	}
}
