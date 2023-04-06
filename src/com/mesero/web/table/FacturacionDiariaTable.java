package com.mesero.web.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mesero.bean.FacturacionDia;
import com.mesero.manageBean.ManagePedido;
import com.vaadin.data.Property;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;

public class FacturacionDiariaTable extends Table {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	private ManagePedido managePedido = new ManagePedido();
	
	public FacturacionDiariaTable() {
		
		setCaption("Facturación Diaria");
		addContainerProperty("Fecha", Date.class, null);
		addContainerProperty("Importe", Double.class, null);
		
		setColumnAlignment("Importe", Align.RIGHT);
		
		setSelectable(true);
        setNullSelectionAllowed(false);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
	}
	
	@Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        Object v = property.getValue();
        if (v instanceof Date) return dateFormat.format((Date) v);
        return super.formatPropertyValue(rowId, colId, property);
    }
	
	public void refresh(Date desde, Date hasta) {
		removeAllItems();
		
		for (FacturacionDia facturacionDia: managePedido.listFacturacionDiaria(desde,hasta)) {
            Object item[] = {
            		facturacionDia.getFecha(),
            		facturacionDia.getImporte()};
            addItem(item, facturacionDia);
        }
		
		setPageLength(size());
	}
}
