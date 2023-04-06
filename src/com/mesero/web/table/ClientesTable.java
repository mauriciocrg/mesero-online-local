package com.mesero.web.table;

import com.mesero.bean.Cliente;
import com.mesero.manageBean.ManageCliente;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Table;

public class ClientesTable extends Table {
		
	private ManageCliente manageCliente = new ManageCliente();
	
	public ClientesTable() {
		setCaption("Clientes");
		addContainerProperty("Teléfono", String.class, null);
		addContainerProperty("Dirección", String.class, null);
		addContainerProperty("E-Mail", String.class, null);
		
		setSelectable(true);
        setNullSelectionAllowed(false);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
        
        refresh();
	}

	public void refresh() {
		removeAllItems();
		
		for (Cliente cliente : manageCliente.listCliente()) {
            Object item[] = {
            		cliente.getTelefono(),
            		cliente.getDireccion(),
            		cliente.getEmail()};
            addItem(item, cliente);
        }
		
		setPageLength(size());
	}
}
