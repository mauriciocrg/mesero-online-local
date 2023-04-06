package com.mesero.web.layout;


import com.mesero.bean.Pedido;
import com.mesero.web.table.PedidosTreeTable;
import com.mesero.web.window.BuscarClienteWindow;
import com.mesero.web.window.IngredientesWindow;
import com.mesero.web.window.PedidoDeliveryWindow;
import com.mesero.web.window.PedidoLocalWindow;
import com.mesero.web.window.PedidoMeseroWindow;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PedidosTabLayout extends VerticalLayout {

	private HorizontalLayout headerLayout = null;
	private Panel tablePanel = new Panel();
	private Button pedidoButton = null;
	private Button actualizarButton = null;
	
	private CheckBox ultimosDosDiasCheck = null;
	
	private PedidosTreeTable pedidosTable = new PedidosTreeTable();
	
	public PedidosTabLayout() {
		removeAllComponents();
		setCaption("Pedidos");
		setSpacing(true);		
		setMargin(new MarginInfo(true,true,true,true));
		setSizeFull();
	
	    tablePanel.setSizeFull();
	    tablePanel.setContent(pedidosTable);
	    
	    addComponent(getHeaderLayout());
	    addComponent(tablePanel);
        setExpandRatio(tablePanel, 1.0f);
        
        pedidosTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()) showItemTable();
			}
        });
	}
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.setSpacing(true);
	        
	    	headerLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
	    	
	        headerLayout.addComponent(getPedidoButton());
	        headerLayout.addComponent(getUltimosDosDiasCheck());
	        headerLayout.addComponent(getActualizarButton());
	        headerLayout.setComponentAlignment(getPedidoButton(), Alignment.MIDDLE_LEFT);
	        headerLayout.setComponentAlignment(getUltimosDosDiasCheck(), Alignment.MIDDLE_CENTER);
	        headerLayout.setComponentAlignment(getActualizarButton(), Alignment.MIDDLE_RIGHT);
		}
		return headerLayout;
	}
	
	private Button getPedidoButton() {
		if(pedidoButton == null) {
			pedidoButton = new Button("Nuevo Pedido Delivery");
			pedidoButton.setIcon(FontAwesome.CART_PLUS);
			//pedidoButton.setWidth(150, Sizeable.Unit.PIXELS);
			pedidoButton.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		getUI().addWindow(new BuscarClienteWindow());
				}
		    });
		}
		return pedidoButton;
	}
	
	
	
	private Button getActualizarButton() {
		if(actualizarButton == null) {
			actualizarButton = new Button();
		    actualizarButton.setIcon(FontAwesome.REFRESH);
		    actualizarButton.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		pedidosTable = new PedidosTreeTable();
		    		pedidosTable.addItemClickListener(new ItemClickListener() {
		    			@Override
		    			public void itemClick(ItemClickEvent event) {
		    				// TODO Auto-generated method stub
		    				if(event.isDoubleClick()) showItemTable();
		    			}
		            });
		    		tablePanel.setContent(pedidosTable);
		    		pedidosTable.refresh(getUltimosDosDiasCheck().getValue());
				}
		    });
		}
		return actualizarButton;
	}
	
	private CheckBox getUltimosDosDiasCheck() {
		if(ultimosDosDiasCheck == null) {
			ultimosDosDiasCheck = new CheckBox("Últimos dos Días");
			ultimosDosDiasCheck.setValue(true);
			ultimosDosDiasCheck.addValueChangeListener(new ValueChangeListener(){
				@Override
				public void valueChange(ValueChangeEvent event) {
					pedidosTable = new PedidosTreeTable();
					pedidosTable.refresh(getUltimosDosDiasCheck().getValue());
					pedidosTable.addItemClickListener(new ItemClickListener() {
						@Override
						public void itemClick(ItemClickEvent event) {
							if(event.isDoubleClick()) showItemTable();
						}
			        });
					tablePanel.setContent(pedidosTable);
				}
			});
		}
		return ultimosDosDiasCheck;
	}
	
	public void refreshTable() {
		pedidosTable = new PedidosTreeTable();
		pedidosTable.refresh(getUltimosDosDiasCheck().getValue());
		pedidosTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()) showItemTable();
			}
        });
		tablePanel.setContent(pedidosTable);
		//System.out.println("Refresh....");
	}
	
	private void showItemTable() {
		if(pedidosTable.getValue() != null) {
			if(pedidosTable.getValue() instanceof Pedido) {
				Pedido pedido = (Pedido)pedidosTable.getValue();
				if(pedido.getTipo_pedido() == Pedido.TIPO_PEDIDO_MOZO && pedido.getEstado() == Pedido.ESTADO_NUEVO) {
					getUI().addWindow(new PedidoMeseroWindow(pedido));
				} else
				if(pedido.getTipo_pedido() == Pedido.TIPO_PEDIDO_LOCAL && (pedido.getEstado() == Pedido.ESTADO_NUEVO || pedido.getEstado() == Pedido.ESTADO_PREPARADO)) {
					getUI().addWindow(new PedidoLocalWindow(pedido));
				}
				if(pedido.getTipo_pedido() == Pedido.TIPO_PEDIDO_DELIVERY && (pedido.getEstado() == Pedido.ESTADO_NUEVO || pedido.getEstado() == Pedido.ESTADO_PREPARADO)) {
					getUI().addWindow(new PedidoDeliveryWindow(pedido));
				}
			}
		} else {
			Notification.show("Debe seleccionar un Item.", Notification.Type.TRAY_NOTIFICATION);
		}
	}
}
