package com.mesero.mobil.window;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.config.Config;
import com.mesero.config.HibernateConfiguration;
import com.mesero.core.Status;
import com.mesero.mobil.component.ButtonMobil;

import com.mesero.manageBean.ManagePedido;
import com.mesero.mobil.component.ButtonMobil;
import com.mesero.mobil.component.LabelMobil;
import com.mesero.mobil.component.TextAreaMobil;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmarPedidoMobilWindow extends Window {

	private Navigator navigator;
	private Pedido pedido;
	private int width;
	private int height;
	private int fontSize;
	private int buttonSize;
	
	private VerticalLayout verticalContentLayout;
	private VerticalLayout verticalFooterLayout;
	private HorizontalLayout horizontalContentLayout;
	private HorizontalLayout horizontalFooterLayout;
	
	private ButtonMobil confirmarButton;
	private ButtonMobil cancelButton;
	
	private TextAreaMobil comentarioTextArea;
	
	private LabelMobil totalLabel;
	
	private ManagePedido managePedido = new ManagePedido();
	
	public ConfirmarPedidoMobilWindow(Navigator navigator, Pedido pedido, int width, int height,int fontSize, int buttonSize) {
		this.navigator = navigator;
		this.pedido = pedido;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize-5;
		this.buttonSize = buttonSize-5;
		
		setModal(true);
		setClosable(false);
		setResizable(false);
		
		if(width < height) {
			setWidth(width-10,Sizeable.Unit.PIXELS);
			setHeight(height*40 /100, Sizeable.Unit.PIXELS);
			setContent(getVerticalContentLayout());
		} else {
			setWidth(width-10,Sizeable.Unit.PIXELS);
			setHeight(height*80 /100, Sizeable.Unit.PIXELS);
			setContent(getHorizontalContentLayout());
		}
	}
	
	
	
	//----Layout----------------------------------------------------------------------------------------------
	
	private VerticalLayout getVerticalContentLayout() {
		if(verticalContentLayout == null) {
			verticalContentLayout = new VerticalLayout();
			verticalContentLayout.setSpacing(false);
			verticalContentLayout.setMargin(false);
			verticalContentLayout.removeAllComponents();
			verticalContentLayout.addComponents(getComentarioTextArea(),getTotalLabel(),getHorizontalFooterLayout());
			//if(width < height) {
			verticalContentLayout.setExpandRatio(getComentarioTextArea(), 0.6f);
			verticalContentLayout.setExpandRatio(getTotalLabel(), 0.2f);
			verticalContentLayout.setExpandRatio(getHorizontalFooterLayout(), 0.2f);
			//}
			verticalContentLayout.setStyleName("v-border");
			verticalContentLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			verticalContentLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return verticalContentLayout;
	}
	
	private HorizontalLayout getHorizontalContentLayout() {
		if(horizontalContentLayout == null) {
			horizontalContentLayout = new HorizontalLayout();
			horizontalContentLayout.setSpacing(false);
			horizontalContentLayout.setMargin(false);
			horizontalContentLayout.removeAllComponents();
			horizontalContentLayout.addComponents(getComentarioTextArea(),getHorizontalFooterLayout());
			//if(width < height) {
			horizontalContentLayout.setExpandRatio(getComentarioTextArea(), 0.6f);
			horizontalContentLayout.setExpandRatio(getHorizontalFooterLayout(), 0.4f);
			//}
			horizontalContentLayout.setStyleName("v-border");
			horizontalContentLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			horizontalContentLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return horizontalContentLayout;
	}
	
	private HorizontalLayout getHorizontalFooterLayout() {
		if(horizontalFooterLayout == null) {
			horizontalFooterLayout = new HorizontalLayout();
			horizontalFooterLayout.setSpacing(false);
			horizontalFooterLayout.setMargin(false);
			horizontalFooterLayout.setStyleName("v-border", true);
			horizontalFooterLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			horizontalFooterLayout.setHeight(buttonSize,Sizeable.Unit.PIXELS);
			horizontalFooterLayout.addComponents(getCancelButton(),getConfirmarButton());
			horizontalFooterLayout.setComponentAlignment(getCancelButton(), Alignment.MIDDLE_CENTER);
			horizontalFooterLayout.setComponentAlignment(getConfirmarButton(), Alignment.MIDDLE_CENTER);
		}
		return horizontalFooterLayout;
	}
	
	private VerticalLayout getVerticalFooterLayout() {
		if(verticalFooterLayout == null) {
			verticalFooterLayout = new VerticalLayout();
			verticalFooterLayout.setSpacing(false);
			verticalFooterLayout.setMargin(false);
			verticalFooterLayout.setStyleName("v-border", true);
			verticalFooterLayout.setHeight(100,Sizeable.Unit.PERCENTAGE);
			verticalFooterLayout.setWidth(buttonSize,Sizeable.Unit.PIXELS);
			verticalFooterLayout.addComponents(getCancelButton(),getConfirmarButton());
			verticalFooterLayout.setComponentAlignment(getCancelButton(), Alignment.MIDDLE_CENTER);
			verticalFooterLayout.setComponentAlignment(getConfirmarButton(), Alignment.MIDDLE_CENTER);
		}
		return verticalFooterLayout;
	}
	
	//----Buttons---------------------------------------------------------------------------------------------
	
	private ButtonMobil getConfirmarButton() {
		if(confirmarButton == null) {
			confirmarButton = new ButtonMobil("Confirmar", FontAwesome.CHECK, fontSize, buttonSize);
			confirmarButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					
					pedido.setEstado(Pedido.ESTADO_NUEVO);
					pedido.setTipo_pedido(Pedido.TIPO_PEDIDO_LOCAL);
					pedido.setFecha(new Date());
					pedido.setComentario(getComentarioTextArea().getValue());
					
					Session session = HibernateConfiguration.getInstance().getSession();
					Transaction tx = null;
					
					try{
						
						session.save(pedido);
						
						tx = session.beginTransaction();
						for(PedidoItem pedidoItemx : pedido.getItems()) {
							pedidoItemx.setPedido(pedido);
							session.save(pedidoItemx);
						}
						 
						tx.commit();
						session.close();
						
					}catch (HibernateException e) {
						if (tx!=null) tx.rollback();
						e.printStackTrace(); 
					}finally {
						if(session.isOpen())session.close();
					}
					
					Status.getInstance().incrementarContador();
					
					navigator.navigateTo("start");
					close();
				}
			});
			confirmarButton.setWidth((Math.min(width, height)/2)-30,Sizeable.Unit.PIXELS);
		}
		return confirmarButton;
	}
	
	private ButtonMobil getCancelButton() {
		if(cancelButton == null) {
			cancelButton = new ButtonMobil("Cancelar", FontAwesome.TIMES, fontSize, buttonSize);
			cancelButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					close();
				}
			});
			cancelButton.setWidth((Math.min(width, height)/2)-30,Sizeable.Unit.PIXELS);
		}
		return cancelButton;
	}
	
	//----TextArea--------------------------------------------------------------------------------------------
	
	private TextAreaMobil getComentarioTextArea() {
		if(comentarioTextArea == null) {
			comentarioTextArea = new TextAreaMobil("Comentario:", fontSize);
			comentarioTextArea.setWidth(100, Sizeable.Unit.PERCENTAGE);
			comentarioTextArea.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return comentarioTextArea;
	}
	
	//----Labels----------------------------------------------------------------------------------------------
	
	private LabelMobil getTotalLabel() {
		if(totalLabel == null) {
			totalLabel = new LabelMobil("Total = $"+getTotal(),fontSize);
		}
		return totalLabel;
	}
	
	//----Logica----------------------------------------------------------------------------------------------
	
	private Double getTotal() {
		double total = 0;
		for(PedidoItem pedidoItem : pedido.getItems()) {
			total = total + pedidoItem.getCantidad() * pedidoItem.getMenuItem().getPrecio();
		}
		return total;
	}
}
