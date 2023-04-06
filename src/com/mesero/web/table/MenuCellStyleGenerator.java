package com.mesero.web.table;

import com.mesero.bean.Menu;
import com.vaadin.ui.Table;

public class MenuCellStyleGenerator implements Table.CellStyleGenerator {

	@Override
	public String getStyle(Table source, Object itemId, Object propertyId) {
		if(itemId instanceof Menu) return "highlight-menu";
		else return null;
	}

}

