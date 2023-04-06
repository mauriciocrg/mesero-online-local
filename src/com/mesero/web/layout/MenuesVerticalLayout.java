package com.mesero.web.layout;

import com.mesero.bean.Menu;
import com.mesero.manageBean.ManageMenu;
import com.mesero.manageBean.ManageMenuItem;
import com.mesero.web.table.MenuesTreeTable;
import com.mesero.web.window.IngredientesWindow;
import com.mesero.web.window.MenuItemWindow;
import com.mesero.web.window.MenuWindow;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class MenuesVerticalLayout extends VerticalLayout {

	private HorizontalLayout headerLayout = null;
	
	private HorizontalLayout buttonsLayout = null;
	
	private MenuesTreeTable menuesTable = new MenuesTreeTable();
	
	private Button buttonIngredientes = null;
	private Button actualizarIngredientes = null;
	
	private MenuBar menubar = null;
	
	private ManageMenu manageMenu = new ManageMenu();
	private ManageMenuItem manageMenuItem = new ManageMenuItem();
	
	public MenuesVerticalLayout() {
		
		removeAllComponents();
		setSpacing(true);
		setMargin(true);
		setMargin(new MarginInfo(true,true,true,true));
		setSizeFull();
		
		Panel tablePanel = new Panel();
	    tablePanel.setSizeFull();
	    tablePanel.setContent(menuesTable);
	   
        addComponent(getHeaderLayout());
        
        addComponent(tablePanel);
        setExpandRatio(tablePanel, 1.0f);
        
        menuesTable.addItemClickListener(new ItemClickListener() {
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
			buttonsLayout.addComponents(getIngredientesButton(),getActualizarButton());
		}
		return buttonsLayout;
	}
	
	//----- BUTTONS ----------------------------------------------------------------------------------------------------------------
	
	private Button getIngredientesButton() {
		if(buttonIngredientes == null) {
			buttonIngredientes = new Button("Ingredientes");
		    buttonIngredientes.setIcon(FontAwesome.FLASK);
		    buttonIngredientes.setWidth(150, Sizeable.Unit.PIXELS);
		    buttonIngredientes.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		getUI().addWindow(new IngredientesWindow());
				}
		    });
		}
		return buttonIngredientes;
	}

	private Button getActualizarButton() {
		if(actualizarIngredientes == null) {
			actualizarIngredientes = new Button();
		    actualizarIngredientes.setIcon(FontAwesome.REFRESH);
		    actualizarIngredientes.addClickListener(new ClickListener(){
		    	@Override
				public void buttonClick(ClickEvent event) {
		    		menuesTable.refresh(manageMenu.listMenu());
				}
		    });
		}
		return actualizarIngredientes;
	}

	
	//----- MENUES ----------------------------------------------------------------------------------------------------------------
	
	private MenuBar getMenuBar() {
		if(menubar == null) {
			menubar = new MenuBar();
			 MenuItem nuevo = menubar.addItem("Nuevo",new MenuBar.Command() {
			    public void menuSelected(MenuItem selectedItem) {
			    	MenuWindow menuWindow = new MenuWindow(null);
			    	menuWindow.addCloseListener(new CloseListener(){
			    		@Override
						public void windowClose(CloseEvent e) {
			    			menuesTable.refresh(manageMenu.listMenu());
						}
			    	});
			    	UI.getCurrent().addWindow(menuWindow);
			    }  
			});       
			MenuItem editar = menubar.addItem("Editar",new MenuBar.Command() {
				public void menuSelected(MenuItem selectedItem) {
					showItemTable();
		    	}  
			});       
			MenuItem borrar = menubar.addItem("Borrar",new MenuBar.Command() {
				public void menuSelected(MenuItem selectedItem) {
					if(menuesTable.getValue() != null) {
						if(menuesTable.getValue() instanceof Menu) {
							manageMenu.deleteMenu(((Menu)menuesTable.getValue()).getNombre_menu());
							menuesTable.refresh(manageMenu.listMenu());
						} else
						if(menuesTable.getValue() instanceof com.mesero.bean.MenuItem) {
							manageMenuItem.deleteMenuItem(((com.mesero.bean.MenuItem)menuesTable.getValue()).getNombre_menuItem());							
							menuesTable.refresh(manageMenuItem.listMenuItem(((com.mesero.bean.MenuItem)menuesTable.getValue()).getMenu().getNombre_menu()));
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
		if(menuesTable.getValue() != null) {
			if(menuesTable.getValue() instanceof Menu) {
				showMenu((Menu)menuesTable.getValue());
			} else
			if(menuesTable.getValue() instanceof com.mesero.bean.MenuItem) {
				showMenuItem((com.mesero.bean.MenuItem)menuesTable.getValue(),(Menu)menuesTable.getParent((com.mesero.bean.MenuItem)menuesTable.getValue()));
			}
		} else {
			Notification.show("Debe seleccionar un Item.", Notification.Type.TRAY_NOTIFICATION);
		}
	}
	
	private void showMenu(Menu menu) {
		MenuWindow menuWindow = new MenuWindow(menu);
		menuWindow.addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				menuesTable.refresh(manageMenu.listMenu());
			}
		});
		getUI().addWindow(menuWindow);
	}
	
	private void showMenuItem(com.mesero.bean.MenuItem menuItem, Menu menu) {
		MenuItemWindow menuItemWindow = new MenuItemWindow(menuItem, menu);
		menuItemWindow.addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {				
				menuesTable.refresh(manageMenu.listMenu());
			}
		});
		getUI().addWindow(menuItemWindow);
	}
}
