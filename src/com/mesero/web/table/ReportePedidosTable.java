package com.mesero.web.table;

import java.util.Set;

import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.manageBean.ManagePedido;
//import com.mesero_online_local.manageBean.ManagePedido;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;

public class ReportePedidosTable extends Table {
	
	private ManagePedido managePedido = new ManagePedido();
	
	public static final String COLUMN_TICKET = "Ticket";
	public static final String COLUMN_NOMBRE = "Nombre";
	public static final String COLUMN_DIRECCION = "Dirección";
	public static final String COLUMN_PRECIO = "Precio";
	public static final String COLUMN_REPORTAR = "Reportar";
	
	public ReportePedidosTable() {
		setCaption("Reporte de Pedidos (Delivery)");
		addContainerProperty("Ticket", Integer.class, null);
		addContainerProperty("Nombre", String.class, null);
		addContainerProperty("Dirección", String.class, null);
		addContainerProperty("Precio", Double.class, null);
		addContainerProperty("Reportar", CheckBox.class, null);
		
		setSelectable(true);
		setFooterVisible(false);
		
		setNullSelectionAllowed(false);
	    setHeight(100, Sizeable.Unit.PERCENTAGE);
	    setWidth(100, Sizeable.Unit.PERCENTAGE);
	    
	    refresh(true);
	}

	public void refresh(boolean ultimosDosDias) {
		removeAllItems();
		
		for (Pedido pedido: managePedido.listPedidoReporte(ultimosDosDias)) {
			
			final CheckBox checkBox = new CheckBox();
			
            Object item[] = {
            		pedido.getId_pedido(),
            		"Teléfono: "+pedido.getCliente().getTelefono(),
            		pedido.getCliente().getDireccion(),
            		getTotal(pedido.getItems()),
            		checkBox};
            
            addItem(item, pedido);
        }
	}
	
	private Double getTotal(Set <PedidoItem> pedidosItem) {
		double total = 0;
		for(PedidoItem pedidoItem : pedidosItem) {
			if(pedidoItem.isHay() == 1) total = total + pedidoItem.getCantidad() * pedidoItem.getMenuItem().getPrecio();
		}
		return total;
	}
	
}
