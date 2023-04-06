package com.mesero.web.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mesero.bean.FacturacionDia;
import com.mesero.bean.FacturacionProducto;
import com.mesero.manageBean.ManagePedido;
import com.vaadin.data.Property;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Table;

public class FacturacionPorProductoTable extends Table {
	
private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private ManagePedido managePedido = new ManagePedido();
	private Double total = new Double(0);
	
	public FacturacionPorProductoTable() {
		
		setCaption("Facturación Por Producto");
		addContainerProperty("Fecha", Date.class, null);
		addContainerProperty("Menu Item/Producto", String.class, null);
		addContainerProperty("Importe", Double.class, null);
		
		setColumnAlignment("Importe", Align.RIGHT);
		
		setSelectable(true);
        setNullSelectionAllowed(false);
        setFooterVisible(true);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
	}
	
	@Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        Object v = property.getValue();
        if (v instanceof Date) return dateFormat.format((Date) v);
        return super.formatPropertyValue(rowId, colId, property);
    }
	
	public void refresh(Date desde, Date hasta, String nombre_menuItem) {
		removeAllItems();
		
		total = new Double(0);
		
		for (FacturacionProducto facturacionProducto: managePedido.listFacturacionPorProducto(desde, hasta, nombre_menuItem)) {
            Object item[] = {
            		facturacionProducto.getFecha(),
            		facturacionProducto.getNombreMenuItem(),
            		facturacionProducto.getImporte()};
            addItem(item, facturacionProducto);
            total = total + facturacionProducto.getImporte();
        }
		
		setColumnFooter("Importe", total.toString());
		
		setPageLength(size());
	}

}
