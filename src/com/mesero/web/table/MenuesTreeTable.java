package com.mesero.web.table;

import java.util.List;

import com.mesero.bean.Menu;
import com.mesero.bean.MenuItem;
import com.mesero.manageBean.ManageMenu;
//import com.mesero_online_local.manageBean.ManageMenu;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.TreeTable;

public class MenuesTreeTable extends TreeTable {
	
	private ManageMenu manageMenu = new ManageMenu();
	
	public MenuesTreeTable() {
		
		setCaption("Menues");
		addContainerProperty("Nombre", String.class, null);
		addContainerProperty("Ingredientes", String.class, null);
		addContainerProperty("Precio", Double.class, null);
		
		setSelectable(true);
        setNullSelectionAllowed(false);
        setHeight(100, Sizeable.Unit.PERCENTAGE);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
        setCellStyleGenerator(new MenuCellStyleGenerator());
        
        refresh(manageMenu.listMenu());
	}

	public void refresh(List <Menu> menues) {
		removeAllItems();
		
		for (Menu menu: menues) {
            Object item[] = {
            		menu.getNombre_menu(),
            		null,
            		null};
            addItem(item, menu);
            for(MenuItem menuItem: menu.getItems()) {
                Object itemMenu[] = {
                		menuItem.getNombre_menuItem(),
                		menuItem.getIngredientes().toString(),
                		menuItem.getPrecio()};
                addItem(itemMenu, menuItem);
                setParent(menuItem, menu);
            }
        }
		
		setPageLength(size());
	}
}
