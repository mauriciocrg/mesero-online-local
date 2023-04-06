package com.mesero.web.table;

import com.mesero.bean.Pedido;
import com.vaadin.ui.Table;

public class PedidoCellStyleGenerator implements Table.CellStyleGenerator {

	@Override
	public String getStyle(Table source, Object itemId, Object propertyId) {
		if(itemId instanceof Pedido) {
			Pedido pedido = (Pedido)itemId;
			if(pedido.getEstado() == Pedido.ESTADO_NUEVO) return "highlight-red";
			else if(pedido.getEstado() == Pedido.ESTADO_PREPARADO) return "highlight-yellow";
			else return "highlight-green";
		} else return null;
	}

}
