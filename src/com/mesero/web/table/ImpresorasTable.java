package com.mesero.web.table;


import com.mesero.core.PrintServer;
import com.mesero.core.PrintServer.PrintDemon;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Table;

public class ImpresorasTable extends Table {
	
	public ImpresorasTable() {
		setCaption("Impresoras");
		addContainerProperty("Dirección IP", String.class, null);
		addContainerProperty("Impresora", String.class, null);
		
		setSelectable(false);
        setNullSelectionAllowed(false);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
        
        setCellStyleGenerator(new ImpresoraCellStyleGenerator());
	}

	
	
	public void refresh() {
		removeAllItems();
		
		for (PrintDemon pd: PrintServer.getInstance().getPrintDemons()) {
            Object item[] = {
            		pd.getHostAddress(),
            		pd.getPrintName()};
            addItem(item, pd);
        }
	}
	
}
