package com.mesero.web.table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.mesero.bean.Menu;
import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.manageBean.ManagePedido;
//import com.mesero_online_local.manageBean.ManagePedido;
//import com.mesero.core.Status;
import com.vaadin.data.Property;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.TreeTable;

public class PedidosTreeTable extends TreeTable {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE HH:mm",new Locale("ES"));
	
	private ManagePedido managePedido = new ManagePedido();
	
	public PedidosTreeTable() {
		setCaption("Pedidos");
		addContainerProperty("Cliente", String.class, null);
		addContainerProperty("Numero de Ticket", Integer.class, null);
		addContainerProperty("Tipo", String.class, null);
		addContainerProperty("Estado", String.class, null);
		addContainerProperty("Hora", Date.class, null);
		addContainerProperty("Total", Double.class, null);
		
		setSelectable(true);
        setNullSelectionAllowed(false);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
        setCellStyleGenerator(new PedidoCellStyleGenerator());
	}
	
	@Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        Object v = property.getValue();
        if (v instanceof Date) return dateFormat.format((Date) v);
        return super.formatPropertyValue(rowId, colId, property);
    }
	
	public void refresh(boolean ultimosDosDias) {
		removeAllItems();
		
		
		
		for (Pedido pedido: managePedido.listPedido(ultimosDosDias)) {
			
			String cliente = "";
			String tipoPedido = "";
			
			switch (pedido.getTipo_pedido()) {
				case Pedido.TIPO_PEDIDO_DELIVERY :
					cliente = "Teléfono: "+pedido.getCliente().getTelefono();
					tipoPedido = "Delivery";
					break;
				case Pedido.TIPO_PEDIDO_LOCAL: 
					cliente = "Mesa N°: "+ pedido.getNumeroMesa();
					tipoPedido = "Pedido Local";
					break;
				case Pedido.TIPO_PEDIDO_MOZO: 
					cliente = "Mesa N°: "+ pedido.getNumeroMesa();
					tipoPedido = "Llama al Mozo";
					break;
			}
			
            Object item[] = {
            		cliente,
            		pedido.getId_pedido(),
            		tipoPedido,
            		pedido.getEstado() == Pedido.ESTADO_NUEVO?"Nuevo":pedido.getEstado() == Pedido.ESTADO_PREPARADO?"Preparado":"Procesado",
            		pedido.getFecha(),
            		getTotal(pedido.getItems())};
            addItem(item, pedido);
            for(PedidoItem pedidoItem: pedido.getItems()) {
                Object itemMenu[] = {
                		null,
                		null,
                		pedidoItem.getMenuItem().getNombre_menuItem()+" - ("+pedidoItem.getCantidad()+")",
                		null,
                		null,
                		pedidoItem.isHay()==1?(pedidoItem.getMenuItem().getPrecio()*pedidoItem.getCantidad() - ((pedidoItem.getDescuento() * pedidoItem.getCantidad() * pedidoItem.getMenuItem().getPrecio())/100)):0};
                addItem(itemMenu, pedidoItem);
                setParent(pedidoItem, pedido);
            }
        }
	}
	
	private Double getTotal(Set <PedidoItem> pedidosItem) {
		double total = 0;
		for(PedidoItem pedidoItem : pedidosItem) {
			if(pedidoItem.isHay() == 1) total = total + pedidoItem.getCantidad() * pedidoItem.getMenuItem().getPrecio() - ((pedidoItem.getDescuento() * pedidoItem.getCantidad() * pedidoItem.getMenuItem().getPrecio())/100);
		}
		return total;
	}
}
