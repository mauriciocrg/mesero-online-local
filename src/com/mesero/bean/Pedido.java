package com.mesero.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Pedido implements Serializable {

	public static final int TIPO_PEDIDO_LOCAL = 1;
	public static final int TIPO_PEDIDO_MOZO = 2;
	public static final int TIPO_PEDIDO_DELIVERY = 3;
	
	public static final int ESTADO_NUEVO = 1;
	public static final int ESTADO_PROCESADO = 2;
	public static final int ESTADO_PREPARADO = 3;
	
	private int id_pedido;

	private int tipo_pedido;
	
	private int estado;
	
	private int numeroMesa;
	
	private String comentario = null;
	
	private Date fecha = null;

	private Cliente cliente = null;

	private Set<PedidoItem> items = new HashSet<PedidoItem>();

	public Pedido() {}
	
	public Pedido(int tipo_pedido,
				  int estado,
				  int numeroMesa,
				  String comentario,
				  Date fecha,
				  Cliente cliente,
				  Set<PedidoItem> items) {
		this.tipo_pedido = tipo_pedido;
		this.estado = estado;
		this.numeroMesa = numeroMesa;
		this.comentario = comentario;
		this.fecha = fecha;
		this.cliente = cliente;
		this.items = items;
	}
	
	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}
	
	public int getTipo_pedido() {
		return tipo_pedido;
	}

	public void setTipo_pedido(int tipo_pedido) {
		this.tipo_pedido = tipo_pedido;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Set<PedidoItem> getItems() {
		return items;
	}

	public void setItems(Set<PedidoItem> items) {
		this.items = items;
	}
}
