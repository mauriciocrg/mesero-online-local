package com.mesero.web.window;


import com.mesero.bean.Ingrediente;
import com.mesero.manageBean.ManageIngrediente;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class IngredienteWindow extends Window {
	
	private VerticalLayout mainLayout = null;
	private VerticalLayout contentCenterLayout = null;
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout headerLayout = null;
	
	private FormLayout formLayout = null;
	
	private Panel centerPanel = null;
	
	private Label headerLabel = null;
	
	private Button aceptarButton = null;
	private Button cancelarButton = null;
	
	private TextField nombreTextField = null;
	
	public IngredienteWindow() {
		setWidth(400, Sizeable.Unit.PIXELS);
		setHeight(205, Sizeable.Unit.PIXELS);
		
		setResizable(false);
		setModal(true);
		setContent(getMainLayout());
		
		getHeaderLabel().setValue("Agregar Ingrediente");
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
			
			footerLayout.addComponent(getAceptarButton());
			footerLayout.setComponentAlignment(getAceptarButton(), Alignment.MIDDLE_CENTER);
			footerLayout.addComponent(getCancelarButton());
			footerLayout.setComponentAlignment(getCancelarButton(), Alignment.MIDDLE_CENTER);
			
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
	
	private FormLayout getFormLayout() {
		if(formLayout == null) {
			formLayout = new FormLayout();
			formLayout.setSpacing(false);
			formLayout.setMargin(false);
			formLayout.addComponent(getNombreTextField());
		}
		return formLayout;
	}
	
	private VerticalLayout getContentCenterLayout() {
		if(contentCenterLayout == null) {
			contentCenterLayout = new VerticalLayout();
			contentCenterLayout.setSpacing(false);
			contentCenterLayout.setMargin(true);
			contentCenterLayout.addComponent(getFormLayout());
		}
		return contentCenterLayout;
	}
	
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			centerPanel.setHeight(70,Sizeable.Unit.PIXELS);
			centerPanel.setContent(getContentCenterLayout());
		}
		return centerPanel;
	}
	
	private TextField getNombreTextField() {
		if(nombreTextField == null) {
			nombreTextField = new TextField("Nombre");
			nombreTextField.setWidth(100, Sizeable.Unit.PERCENTAGE);
			nombreTextField.setRequired(true);
		}
		return nombreTextField;
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
					if(getNombreTextField().getValue().isEmpty()) {
						Notification.show("El Nombre no puede ser vacio.", Notification.Type.TRAY_NOTIFICATION);
					} else {
										
						ManageIngrediente manageIngrediente = new ManageIngrediente();
						Ingrediente ingrediente = manageIngrediente.getIngrediente(getNombreTextField().getValue());
						
						if(ingrediente != null) {
							Notification.show("Ya existe un Ingrediente con este Nombre.", Notification.Type.TRAY_NOTIFICATION);
						} else {
							ingrediente = new Ingrediente();
							ingrediente.setNombre(getNombreTextField().getValue());
							manageIngrediente.saveIngrediente(ingrediente);
							close();
						}
					}
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
}
