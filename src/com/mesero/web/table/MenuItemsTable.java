package com.mesero.web.table;

import java.util.List;
import java.util.Set;

import com.mesero.bean.MenuItem;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Table;

public class MenuItemsTable extends Table {

	public MenuItemsTable() {
		
		setCaption("Menu Items");
		addContainerProperty("Nombre", String.class, null);
		addContainerProperty("Ingredientes", String.class, null);
		addContainerProperty("Precio", Double.class, null);
		
		setSelectable(true);
        setNullSelectionAllowed(false);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
	}
	
	public void refresh(Set<MenuItem> items) {
		removeAllItems();
		
		for (MenuItem menuItem : items) {
            Object item[] = {
            		menuItem.getNombre_menuItem(),
            		menuItem.getIngredientes().toString(),
            		menuItem.getPrecio()};
            addItem(item, menuItem);
        }
		
		setPageLength(size());
	}
}
