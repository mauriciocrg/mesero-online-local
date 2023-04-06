package com.mesero.web.table;

import java.util.List;
import java.util.Set;

import com.mesero.bean.MenuItem;
import com.mesero.bean.PedidoItem;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;

import de.akquinet.engineering.vaadin.html5.widgetset.NumberField;

public class PedidoItemsTable extends Table {

	private Set<PedidoItem> items;
	private int descuento = 0;
	private double total = 0;
	
	public PedidoItemsTable(Set<PedidoItem> items) {
		this.items = items;
		
		setCaption("Pedido Items");
		addContainerProperty("Nombre", String.class, null);
		addContainerProperty("Ingredientes", String.class, null);
		addContainerProperty("Cantidad", Integer.class, null);
		addContainerProperty("Precio", Double.class, null);
		addContainerProperty("Descuento", NumberField.class, null);
		addContainerProperty("Hay", CheckBox.class, null);
	
		
		setSelectable(true);
		setFooterVisible(true);
		setPageLength(4);
        setNullSelectionAllowed(false);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
        
        refresh(0);
	}
	
	public void refresh(final int descuento) {
		
		this.descuento = descuento;
		
		total = 0;
		
		removeAllItems();
		
		for (final PedidoItem pedidoItem : items) {
			
			pedidoItem.setDescuento(descuento);
			
			final CheckBox checkBox = new CheckBox();
			checkBox.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					pedidoItem.setHay(checkBox.getValue()?1:0);
					updateTotal();
				}
			});
			
			final NumberField descuentoField = new NumberField();
			descuentoField.setWidth(60, Sizeable.Unit.PIXELS);
			descuentoField.setMax(100);
			descuentoField.setMin(0);
			descuentoField.setValue(""+descuento);
			descuentoField.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					pedidoItem.setDescuento(descuento==0?new Integer(descuentoField.getValue()):descuento);
					updateTotal();
				}
			});
			
			if(pedidoItem.isHay()==1) {
				checkBox.setValue(true);
				total = total + (pedidoItem.getMenuItem().getPrecio()*pedidoItem.getCantidad() - ((pedidoItem.getDescuento()*pedidoItem.getMenuItem().getPrecio()*pedidoItem.getCantidad())/100));
			}
			
            Object item[] = {
            		pedidoItem.getMenuItem().getNombre_menuItem(),
            		pedidoItem.getMenuItem().getIngredientes().toString(),
            		pedidoItem.getCantidad(),
            		pedidoItem.getMenuItem().getPrecio()*pedidoItem.getCantidad(),
            		descuentoField,
            		checkBox};
            
            addItem(item, pedidoItem);
            
            
        }
		
		setColumnFooter("Cantidad", "Total:");
		setColumnFooter("Precio", ""+total);
		setPageLength(size());
	}
	
	private void updateTotal() {
		double total = 0;
		for (final PedidoItem pedidoItem : items) {
			if(pedidoItem.isHay()==1) total = total + (pedidoItem.getMenuItem().getPrecio()*pedidoItem.getCantidad() - ((pedidoItem.getDescuento()*pedidoItem.getMenuItem().getPrecio()*pedidoItem.getCantidad())/100));
		}
		setColumnFooter("Precio", ""+total);
	}
	
	public double getTotal() {
		return total;
	}
}
