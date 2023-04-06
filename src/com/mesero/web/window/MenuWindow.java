package com.mesero.web.window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.mesero.bean.Menu;
import com.mesero.config.HibernateConfiguration;

import com.mesero.manageBean.ManageMenu;
import com.mesero.manageBean.ManageMenuItem;
import com.mesero.web.table.MenuItemsTable;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class MenuWindow extends Window {
	
	private VerticalLayout mainLayout = null;
	
	private VerticalLayout formContentLayout = null;
	
	private VerticalLayout menuItemsLayout = null;
	
	private HorizontalLayout footerLayout = null;
	
	private HorizontalLayout headerLayout = null;
	
	private FormLayout formLayout = null;

	private Panel centerPanel = null;
	private Panel menuItemsTablePanel = null;
	private Panel menuItemsContentPanel = null;
	
	private MenuBar menubar = null;
	
	private TextField nombreTextField = null;
	
	private Label headerLabel = null;
	
	private Button aceptarButton = null;
	private Button cancelarButton = null;
	
	private MenuItemsTable menuItemsTable = null;

	private Menu menu = null;
	private Menu menuAux = new Menu();
	
	private ManageMenu manageMenu = new ManageMenu();
	private ManageMenuItem manageMenuItem = new ManageMenuItem();
	
	public MenuWindow(Menu menu) {
		
		this.menu = menu;
		
		setWidth(700, Sizeable.Unit.PIXELS);
		setHeight(600, Sizeable.Unit.PIXELS);
		
		setResizable(false);
		setModal(true);
		setContent(getMainLayout());
		
		if(this.menu != null) { 
			setData();
			getHeaderLabel().setValue("Editar Menu");
		} else {
			getHeaderLabel().setValue("Nuevo Menu");
		}
	}
	
	private FormLayout getFormLayout() {
		if(formLayout == null) {
			formLayout = new FormLayout();
			//formLayout.setSpacing(true);
			formLayout.setMargin(false);
			//formLayout.setMargin(new MarginInfo(false,false,true,false));
			formLayout.addComponent(getNombreTextField());
		}
		return formLayout;
	}
	
	private VerticalLayout getFormContentLayout() {
		if(formContentLayout == null) {
			formContentLayout = new VerticalLayout();
			formContentLayout.setSpacing(true);
			formContentLayout.setMargin(true);
			formContentLayout.addComponents(getFormLayout(), getMenuItemsContentPanel());
		}
		return formContentLayout;
	}
	
	private HorizontalLayout getFooterLayout() {
		if(footerLayout == null) {
			footerLayout = new HorizontalLayout();
			footerLayout.setSpacing(true);
			footerLayout.setMargin(false);
			
			footerLayout.addComponent(getAceptarButton());
			footerLayout.setComponentAlignment(getAceptarButton(), Alignment.MIDDLE_CENTER);
			footerLayout.addComponent(getCancelarButton());
			footerLayout.setComponentAlignment(getCancelarButton(), Alignment.MIDDLE_CENTER);
			
			footerLayout.setHeight(70,Sizeable.Unit.PIXELS);
		}
		return footerLayout;
	}
	
	private VerticalLayout getMainLayout() {
		if(mainLayout == null) {
			mainLayout = new VerticalLayout();
			mainLayout.addComponent(getHeaderLayout());
			mainLayout.setComponentAlignment(getHeaderLayout(), Alignment.TOP_CENTER);
			mainLayout.addComponent(getCenterPanel());
			mainLayout.setComponentAlignment(getCenterPanel(), Alignment.MIDDLE_CENTER);
			mainLayout.addComponent(getFooterLayout());
			mainLayout.setComponentAlignment(getFooterLayout(), Alignment.BOTTOM_CENTER);
			mainLayout.setMargin(false);
		}
		return mainLayout;
	}
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.addComponent(getHeaderLabel());
		}
		return headerLayout;
	}
	
	private VerticalLayout getMenuItemsLayout() {
		if(menuItemsLayout == null) {
			menuItemsLayout = new VerticalLayout();
			menuItemsLayout.setSpacing(true);
			menuItemsLayout.setMargin(true);
			menuItemsLayout.addComponents(getMenuBar(), getMenuItemsTablePanel());
			menuItemsLayout.setExpandRatio(getMenuItemsTablePanel(), 1.0f);
		}
		return menuItemsLayout;
	}
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setSizeFull();
			centerPanel.setContent(getFormContentLayout());
		}
		return centerPanel;
	}
	
	private Panel getMenuItemsContentPanel() {
		if(menuItemsContentPanel == null) {
			menuItemsContentPanel = new Panel("Menu Items");
			menuItemsContentPanel.setHeight(390, Sizeable.Unit.PIXELS);
			menuItemsContentPanel.setContent(getMenuItemsLayout());
		}
		return menuItemsContentPanel;
	} 	
	
	private Panel getMenuItemsTablePanel() {
		if(menuItemsTablePanel == null) {
			menuItemsTablePanel = new Panel();
			menuItemsTablePanel.setHeight(275, Sizeable.Unit.PIXELS);
			menuItemsTablePanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			menuItemsTablePanel.setContent(getMenuItemsTable());
		}
		return menuItemsTablePanel;
	}
	
	private TextField getNombreTextField() {
		if(nombreTextField == null) {
			nombreTextField = new TextField("Nombre");
			//nombreTextField.setMaxLength(Factura.ID_MAX_LENGTH);
			nombreTextField.setRequired(true);
		}
		return nombreTextField;
	}
	
	private MenuBar getMenuBar() {
		if(menubar == null) {
			menubar = new MenuBar();
			 MenuItem nuevo = menubar.addItem("Nuevo",new MenuBar.Command() {
			    public void menuSelected(MenuItem selectedItem) {
			    	MenuItemWindow menuItemWindow = new MenuItemWindow(null,menu == null? menuAux:menu);
			    	menuItemWindow.addCloseListener(new CloseListener(){
			    		@Override
						public void windowClose(CloseEvent e) {
							// TODO Auto-generated method stub
			    			getMenuItemsTable().refresh(menu == null? menuAux.getItems():menu.getItems());
						}
			    	});
			    	UI.getCurrent().addWindow(menuItemWindow);
			    }  
			});       
			MenuItem editar = menubar.addItem("Editar",new MenuBar.Command() {
				public void menuSelected(MenuItem selectedItem) {
					showMenuItem();
		    	}  
			});       
			MenuItem borrar = menubar.addItem("Borrar",new MenuBar.Command() {
				public void menuSelected(MenuItem selectedItem) {
					if(getMenuItemsTable().getValue() != null) {
						if(menu == null) {
							menuAux.getItems().remove((com.mesero.bean.MenuItem)getMenuItemsTable().getValue());
						} else {
							com.mesero.bean.MenuItem mi = manageMenuItem.getMenuItem(((com.mesero.bean.MenuItem)getMenuItemsTable().getValue()).getNombre_menuItem());
							if(mi != null) {
								manageMenuItem.deleteMenuItem(((com.mesero.bean.MenuItem)getMenuItemsTable().getValue()).getNombre_menuItem());
							} 
							menu.getItems().remove((com.mesero.bean.MenuItem)getMenuItemsTable().getValue());
						}
						getMenuItemsTable().refresh(menu == null? menuAux.getItems():menu.getItems());
					} else {
						Notification.show("Debe seleccionar un Item de la tabla.", Notification.Type.TRAY_NOTIFICATION);
					}
		    	}  
			});       
		}
		return menubar;
	}
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label();
			headerLabel.setStyleName("h2");
		}
		return headerLabel;
	}
	
	private Button getAceptarButton() {
		if(aceptarButton == null) {
			aceptarButton = new Button("Aceptar");
			aceptarButton.setWidth(150, Sizeable.Unit.PIXELS);
			aceptarButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					procesar();
				}
			});
		}
		return aceptarButton;
	}
	
	private Button getCancelarButton() {
		if(cancelarButton == null) {
			cancelarButton = new Button("Cancelar");
			cancelarButton.setWidth(150, Sizeable.Unit.PIXELS);
			cancelarButton.addClickListener(new ClickListener() {
				 @Override
				 public void buttonClick(final ClickEvent event) {
					 close();
				 }
			});
		}
		return cancelarButton;
	}
	
	private MenuItemsTable getMenuItemsTable() {
		if(menuItemsTable == null) {
			menuItemsTable = new MenuItemsTable();
			menuItemsTable.addItemClickListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					// TODO Auto-generated method stub
					if(event.isDoubleClick()) {
						showMenuItem();
					}
				}
			});
		}
		return menuItemsTable;
	}
	//-----------------------------------------------------------------------------------------------
	
	private void setData() {
		getNombreTextField().setValue(menu.getNombre_menu());
		getNombreTextField().setReadOnly(true);
		getMenuItemsTable().refresh(menu.getItems());
	}
	
	private boolean Validar() {
		
		try {
			getNombreTextField().validate();
		} catch (InvalidValueException e) {
		    Notification.show("El valor del Nombre no puede ser vacio.", Notification.Type.TRAY_NOTIFICATION);
		    getNombreTextField().setValidationVisible(true);
		    return false;
		}
		
		if(getMenuItemsTable().getItemIds().size() == 0) {
			Notification.show("El Menu debe tener al menos un Item.", Notification.Type.TRAY_NOTIFICATION);
			return false;
		}
		
		return true;
	}	
	
	private void showMenuItem() {
		if(getMenuItemsTable().getValue() != null) {
			com.mesero.bean.MenuItem selected = (com.mesero.bean.MenuItem)getMenuItemsTable().getValue();
			MenuItemWindow menuItemWindow = new MenuItemWindow(selected,menu == null? menuAux:menu);
			menuItemWindow.addCloseListener(new CloseListener(){
				@Override
				public void windowClose(CloseEvent e) {
					getMenuItemsTable().refresh(menu == null? menuAux.getItems():menu.getItems());
				}
			});
			getUI().addWindow(menuItemWindow);
		} else {
			Notification.show("Debe seleccionar un Item de la tabla.", Notification.Type.TRAY_NOTIFICATION);
		}
	}
	
	private void saveOrUpdate() {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			
			session.saveOrUpdate(menu);
			
			tx = session.beginTransaction();
			for(com.mesero.bean.MenuItem menuItemx : menu.getItems()) {
				menuItemx.setMenu(menu);
				session.saveOrUpdate(menuItemx);
			}
			 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close();
		}
	}
	
	private void procesar() {
		if(Validar()) {
			if(getNombreTextField().isValid() && 
			   getMenuItemsTable().getItemIds().size() > 0) {
				if(menu != null) {
					menu.setNombre_menu(getNombreTextField().getValue());
					ArrayList <com.mesero.bean.MenuItem> arrayList = new ArrayList <com.mesero.bean.MenuItem> ();
					arrayList.addAll(Arrays.asList(getMenuItemsTable().getItemIds().toArray(new com.mesero.bean.MenuItem[getMenuItemsTable().getItemIds().size()])));
					menu.setItems(new HashSet<com.mesero.bean.MenuItem>(arrayList));
					saveOrUpdate();
					close();
				} else {
					
					menu = manageMenu.getMenu(getNombreTextField().getValue());
					
					if(menu != null) {
						Notification.show("Ya existe un Menu con Nombre = "+getNombreTextField().getValue(), Notification.Type.TRAY_NOTIFICATION);
						menu = null;
					} else {
						menu = new Menu();
						menu.setNombre_menu(getNombreTextField().getValue());
						ArrayList <com.mesero.bean.MenuItem> arrayList = new ArrayList <com.mesero.bean.MenuItem> ();
						arrayList.addAll(Arrays.asList(getMenuItemsTable().getItemIds().toArray(new com.mesero.bean.MenuItem[getMenuItemsTable().getItemIds().size()])));
						menu.setItems(new HashSet<com.mesero.bean.MenuItem>(arrayList));
						saveOrUpdate();
						close();
					}
				}
			}
		}
	}
}
