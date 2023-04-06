package com.mesero.bean;

import java.io.Serializable;

public class PedidoItem implements Serializable {
 
	private int id_pedidoItem;
	
	private int cantidad;
	
	private int hay; //1=true, 0=false
	
	private int descuento;

	private MenuItem menuItem = null;
	
	private Pedido pedido = null;
	
	public PedidoItem() {}
	public PedidoItem(int cantidad,
					  int hay,
					  MenuItem menuItem,
					  Pedido pedido,
					  int descuento) {
		this.cantidad = cantidad;
		this.hay = hay;
		this.menuItem = menuItem;
		this.pedido = pedido;
		this.descuento = descuento;
	}
	
	public int getId_pedidoItem() {
		return id_pedidoItem;
	}

	public void setId_pedidoItem(int id_pedidoItem) {
		this.id_pedidoItem = id_pedidoItem;
	}
	
	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public int isHay() {
		return hay;
	}

	public void setHay(int hay) {
		this.hay = hay;
	}
	
	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
}
