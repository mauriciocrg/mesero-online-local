package com.mesero.web.window;

import com.mesero.bean.Ingrediente;
import com.mesero.manageBean.ManageIngrediente;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.Window;

public class IngredientesWindow extends Window {

	private VerticalLayout mainLayout = null;
	private VerticalLayout contentCenterLayout = null;
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout headerLayout = null;
	
	private Panel centerPanel = null;
	
	private ListSelect ingredientesList = null;
	
	private Label headerLabel = null;
	
	private MenuBar menubar = null;
	
	private Button cerrarButton = null;
	
	private ManageIngrediente manageIngrediente = new ManageIngrediente();
	
	public IngredientesWindow() {

		setWidth(400, Sizeable.Unit.PIXELS);
		setHeight(459, Sizeable.Unit.PIXELS);
		
		setClosable(false);
		setResizable(false);
		setModal(true);
		setContent(getMainLayout());
		
		getHeaderLabel().setValue("Ingredientes");
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

	
	private HorizontalLayout getFooterLayout() {
		if(footerLayout == null) {
			footerLayout = new HorizontalLayout();
			footerLayout.setSpacing(true);
			footerLayout.addComponent(getCerrarButton());
			footerLayout.setComponentAlignment(getCerrarButton(), Alignment.MIDDLE_CENTER);
			footerLayout.setHeight(70,Sizeable.Unit.PIXELS);
		}
		return footerLayout;
	}
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.addComponent(getHeaderLabel());
		}
		return headerLayout;
	}
	
	private VerticalLayout getContentCenterLayout() {
		if(contentCenterLayout == null) {
			contentCenterLayout = new VerticalLayout();
			contentCenterLayout.setSpacing(true);
			contentCenterLayout.setMargin(true);
			contentCenterLayout.addComponents(getIngredientesList(), getMenuBar());
			contentCenterLayout.setComponentAlignment(getIngredientesList(), Alignment.MIDDLE_CENTER);
			contentCenterLayout.setComponentAlignment(getMenuBar(), Alignment.BOTTOM_CENTER);
		}
		return contentCenterLayout;
	}
	
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			centerPanel.setHeight(320,Sizeable.Unit.PIXELS);
			centerPanel.setContent(getContentCenterLayout());
		}
		return centerPanel;
	}
	
	private ListSelect getIngredientesList() {
		if(ingredientesList == null) {
			ingredientesList = new ListSelect();
			ingredientesList.setNullSelectionAllowed(false);
			ingredientesList.setWidth(100, Sizeable.Unit.PERCENTAGE);
			ingredientesList.addItems(manageIngrediente.listIngrediente());
		}
		return ingredientesList;
	}
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label();
			headerLabel.setStyleName("h2");
		}
		return headerLabel;
	}
	
	
	private MenuBar getMenuBar() {
		if(menubar == null) {
			menubar = new MenuBar();
			com.vaadin.ui.MenuBar.MenuItem nuevo = menubar.addItem("Nuevo",new MenuBar.Command() {
			    public void menuSelected(com.vaadin.ui.MenuBar.MenuItem selectedItem) {
			    	IngredienteWindow ingredienteWindow = new IngredienteWindow();
			    	ingredienteWindow.addCloseListener(new CloseListener(){
			    		@Override
						public void windowClose(CloseEvent e) {
							// TODO Auto-generated method stub
			    			getIngredientesList().removeAllItems();
			    			getIngredientesList().addItems(manageIngrediente.listIngrediente());
						}
			    	});
			    	UI.getCurrent().addWindow(ingredienteWindow);
			    }  
			});       
			    
			com.vaadin.ui.MenuBar.MenuItem borrar = menubar.addItem("Borrar",new MenuBar.Command() {
				@Override
				public void menuSelected(com.vaadin.ui.MenuBar.MenuItem selectedItem) {
					if(ingredientesList.getValue() != null) {
						manageIngrediente.deleteIngrediente(((Ingrediente)ingredientesList.getValue()).getId());
						getIngredientesList().removeAllItems();
		    			getIngredientesList().addItems(manageIngrediente.listIngrediente());
					} else {
						Notification.show("Debe seleccionar un Item de la Lista.",Notification.Type.TRAY_NOTIFICATION);
					}
				}  
			});       
		}
		return menubar;
	}
	
	private Button getCerrarButton() {
		if(cerrarButton == null) {
			cerrarButton = new Button("Cerrar");
			cerrarButton.setWidth(150, Sizeable.Unit.PIXELS);
			cerrarButton.addClickListener(new ClickListener() {
				 @Override
				 public void buttonClick(final ClickEvent event) {
					 close();
				 }
			});
		}
		return cerrarButton;
	}
}
