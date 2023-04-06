package com.mesero.web.layout;


import com.mesero.bean.Cliente;
import com.mesero.manageBean.ManageCliente;
import com.mesero.web.table.ClientesTable;
import com.mesero.web.window.ClienteWindow;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class ClientesVerticalLayout extends VerticalLayout {

	private HorizontalLayout headerLayout = null;
	
	private HorizontalLayout buttonsLayout = null;
	
	private ClientesTable clientesTable = new ClientesTable();
	
	private Button actualizarButton = null;
	
	private MenuBar menubar = null;
	
	private ManageCliente manageCliente = new ManageCliente();
	
	public ClientesVerticalLayout() {
		
		removeAllComponents();
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		
		Panel tablePanel = new Panel();
	    tablePanel.setSizeFull();
	    tablePanel.setContent(clientesTable);
	   
        addComponent(getHeaderLayout());
        
        addComponent(tablePanel);
        setExpandRatio(tablePanel, 1.0f);
        
        clientesTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				// TODO Auto-generated method stub
				if(event.isDoubleClick()) showItemTable();
			}
        });
    }
	
	//----- LAYOUTS ----------------------------------------------------------------------------------------------------------------
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.setSpacing(true);
	        
	    	headerLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
	    	
	        headerLayout.addComponents(getMenuBar(),getButtonsLayout());
	        headerLayout.setComponentAlignment(getMenuBar(), Alignment.TOP_LEFT);
	        headerLayout.setComponentAlignment(getButtonsLayout(), Alignment.TOP_RIGHT);
		}
		return headerLayout;
	}
	
	private HorizontalLayout getButtonsLayout() {
		if(buttonsLayout == null) {
			buttonsLayout = new HorizontalLayout();
			buttonsLayout.setMargin(false);
			buttonsLayout.setSpacing(true);
			buttonsLayout.addComponents(getActualizarButton());
		}
		return buttonsLayout;
	}
	
	//----- BUTTONS ----------------------------------------------------------------------------------------------------------------

	private Button getActualizarButton() {
		if(actualizarButton == null) {
			actualizarButton = new Button();
		    actualizarButton.setIcon(FontAwesome.REFRESH);
		    actualizarButton.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		clientesTable.refresh();
				}
		    });
		}
		return actualizarButton;
	}

	
	//----- MENUES ----------------------------------------------------------------------------------------------------------------
	
	private MenuBar getMenuBar() {
		if(menubar == null) {
			menubar = new MenuBar();
			 MenuItem nuevo = menubar.addItem("Nuevo",new MenuBar.Command() {
			    public void menuSelected(MenuItem selectedItem) {
			    	ClienteWindow clienteWindow = new ClienteWindow(null);
			    	clienteWindow.addCloseListener(new CloseListener(){
			    		@Override
						public void windowClose(CloseEvent e) {
			    			clientesTable.refresh();
						}
			    	});
			    	getUI().addWindow(clienteWindow);
			    }  
			});       
			MenuItem editar = menubar.addItem("Editar",new MenuBar.Command() {
				public void menuSelected(MenuItem selectedItem) {
					showItemTable();
		    	}  
			});       
			MenuItem borrar = menubar.addItem("Borrar",new MenuBar.Command() {
				public void menuSelected(MenuItem selectedItem) {
					if(clientesTable.getValue() != null) {
						if(clientesTable.getValue() instanceof Cliente) {
							manageCliente.deleteCliente(((Cliente)clientesTable.getValue()).getTelefono());
							clientesTable.refresh();
						} 
					} else {
						Notification.show("Debe seleccionar un Item.", Notification.Type.TRAY_NOTIFICATION);
					}
		    	}  
			});       
		}
		return menubar;
	}
	
	private void showItemTable() {
		if(clientesTable.getValue() != null) {
			if(clientesTable.getValue() instanceof Cliente) {
				ClienteWindow clienteWindow = new ClienteWindow((Cliente)clientesTable.getValue());
				clienteWindow.addCloseListener(new CloseListener() {
					@Override
					public void windowClose(CloseEvent e) {
						clientesTable.refresh();
					}
				});
				getUI().addWindow(clienteWindow);
			}
		} else {
			Notification.show("Debe seleccionar un Item.", Notification.Type.TRAY_NOTIFICATION);
		}
	}
}
