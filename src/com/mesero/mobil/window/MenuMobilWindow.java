package com.mesero.mobil.window;

import java.util.Date;

import com.mesero.bean.Pedido;
import com.mesero.mobil.component.ButtonMobil;
import com.mesero.manageBean.ManagePedido;
import com.mesero.mobil.component.ButtonMobil;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MenuMobilWindow extends Window {

	private ButtonMobil salirButton;
	private ButtonMobil llamarMeseroButton;
	private ButtonMobil realizarPedidoButton;
	private ButtonMobil cambiarMesaButton;
	
	private VerticalLayout contentLayout;
	
	private int width;
	private int height;
	private int fontSize;
	private int buttonSize;
	private int numMesa;
	private Navigator navigator;
	private Pedido pedido;
	
	private ManagePedido managePedido = new ManagePedido();
	
	public MenuMobilWindow(Pedido pedido, Navigator navigator, int width, int height, int fontSize, int buttonSize) {
		this.pedido = pedido;
		this.navigator = navigator;
		this.numMesa = pedido.getNumeroMesa();
		this.width = width;
		this.height = height;
		this.fontSize = fontSize - 10;
		this.buttonSize = buttonSize - 10;
		setModal(true);
		setClosable(false);
		setResizable(false);
		setWidth(Math.min(width,height) - 20, Sizeable.Unit.PIXELS);
		setContent(getContentLayout());
	}
	
	private VerticalLayout getContentLayout() {
		if(contentLayout == null) {
			contentLayout = new VerticalLayout();
			contentLayout.setStyleName("v-border",true);
			contentLayout.setSpacing(true);
			contentLayout.addComponents(getRealizarPedidoButton(),getLlamarMeseroButton(),getCambiarMesaButton(),getSalirButton());
		}
		return contentLayout;
	}
	
	//--- Buttons ---------------------------------------------------------------------------------------------
	
	private ButtonMobil getSalirButton() {
		if(salirButton == null) {
			salirButton = new ButtonMobil("Salir",FontAwesome.SIGN_OUT,fontSize,buttonSize);
			salirButton.setWidth(Math.min(width,height) - 30, Sizeable.Unit.PIXELS);
			salirButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					close();
				}
			});
		}
		return salirButton;
	} 
	
	private ButtonMobil getLlamarMeseroButton() {
		if(llamarMeseroButton == null) {
			llamarMeseroButton = new ButtonMobil("Llamar al Mesero",FontAwesome.COMMENT_O,fontSize,buttonSize);
			llamarMeseroButton.setWidth(Math.min(width,height) - 30, Sizeable.Unit.PIXELS);
			llamarMeseroButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					Pedido pedido = new Pedido();
					pedido.setTipo_pedido(Pedido.TIPO_PEDIDO_MOZO);
					pedido.setEstado(Pedido.ESTADO_NUEVO);
					pedido.setFecha(new Date());
					pedido.setNumeroMesa(numMesa);
					
					managePedido.savePedido(pedido);
					
					close();
				}
			});
			//
		}
		return llamarMeseroButton;
	}
	
	private ButtonMobil getRealizarPedidoButton() {
		if(realizarPedidoButton == null) {
			realizarPedidoButton = new ButtonMobil("Realizar el Pedido",FontAwesome.CUTLERY,fontSize,buttonSize);
			realizarPedidoButton.setWidth(Math.min(width,height) - 30, Sizeable.Unit.PIXELS);
			realizarPedidoButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					if(pedido.getItems().isEmpty()) {
						Notification.show("No hay items en el pedido.", Notification.Type.TRAY_NOTIFICATION);
					} else {
						getUI().addWindow(new ConfirmarPedidoMobilWindow(navigator, pedido, width, height, fontSize, buttonSize));
					}
					close();
				}
			});
		}
		return realizarPedidoButton;
	}
	
	private ButtonMobil getCambiarMesaButton() {
		if(cambiarMesaButton == null) {
			cambiarMesaButton = new ButtonMobil("Cambiar de Mesa",FontAwesome.MAP_MARKER,fontSize,buttonSize);
			cambiarMesaButton.setWidth(Math.min(width,height) - 30, Sizeable.Unit.PIXELS);
			cambiarMesaButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo("start");
					close();
				}
			});
		}
		return cambiarMesaButton;
	}
}
