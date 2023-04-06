package com.mesero.web.table;



import com.mesero.config.Config;
import com.mesero.core.PrintServer.PrintDemon;
import com.vaadin.ui.Table;

public class ImpresoraCellStyleGenerator implements Table.CellStyleGenerator {
	
	@Override
	public String getStyle(Table source, Object itemId, Object propertyId) {
		if(itemId instanceof PrintDemon && ((PrintDemon)itemId).getHostAddress().equals(Config.getInstance().getClientIP())) return "highlight-green";
		else return null;
	}

}