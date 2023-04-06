package com.mesero.web.window;

import com.mesero.bean.Cliente;
import com.mesero.manageBean.ManageCliente;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class BuscarClienteWindow extends Window {

	private Panel mainPanel = null;
	
	private VerticalLayout mainLayout = null;
	
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout centerLayout = null;
	private HorizontalLayout headerLayout = null;
	
	private TextField telefonoField = null;
	
	private Button buscarButton = null;
	private Button nuevoClienteButton = null;
	private Button cancelarButton = null;
	
	private Label headerLabel;
	private Label telefonoLabel;
	
	private ManageCliente manageCliente = new ManageCliente();
	
	public BuscarClienteWindow() {
		setWidth(550, Sizeable.Unit.PIXELS);
		setHeight(200, Sizeable.Unit.PIXELS);
		setResizable(false);
		setModal(true);
		setContent(getMainLayout());
	}
	
	//-----Layouts-----------------------------------------------------------------------------
	
	
	private VerticalLayout getMainLayout() {
		if(mainLayout == null) {
			mainLayout = new VerticalLayout();
			mainLayout.addComponent(getHeaderLayout());
			mainLayout.setComponentAlignment(getHeaderLayout(), Alignment.TOP_CENTER);
			mainLayout.addComponent(getMainPanel());
			mainLayout.setComponentAlignment(getMainPanel(), Alignment.MIDDLE_CENTER);
			mainLayout.addComponent(getFooterLayout());
			mainLayout.setComponentAlignment(getFooterLayout(), Alignment.BOTTOM_CENTER);
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
	
	private HorizontalLayout getCenterLayout() {
		if(centerLayout == null) {
			centerLayout = new HorizontalLayout();
			centerLayout.setSpacing(true);
			centerLayout.setMargin(true);
			centerLayout.addComponents(getTelefonoLabel(),getTelefonoTextField(),getBuscarButton(),getNuevoClienteButton());
		}
		return centerLayout;
	}
	
	private HorizontalLayout getFooterLayout() {
		if(footerLayout == null) {
			footerLayout = new HorizontalLayout();
			footerLayout.addComponent(getCancelarButton());
			footerLayout.setComponentAlignment(getCancelarButton(), Alignment.MIDDLE_CENTER);
			footerLayout.setHeight(70,Sizeable.Unit.PIXELS);
		}
		return footerLayout;
	}
	
	private Panel getMainPanel() {
		if(mainPanel == null) {
			mainPanel = new Panel();
			mainPanel.setSizeFull();
			mainPanel.setContent(getCenterLayout());
		}
		return mainPanel;
	}
	
	//-----Buttons-----------------------------------------------------------------------------
	
	private Button getBuscarButton() {
		if(buscarButton == null) {
			buscarButton = new Button(FontAwesome.SEARCH);
			buscarButton.addClickListener(new ClickListener() {
				 public void buttonClick(final ClickEvent event) {
					 Cliente cliente = manageCliente.getCliente(getTelefonoTextField().getValue());
					 
					 if(cliente == null) {
						 Notification.show("No existe Cliente con dicho Teléfono.", Notification.Type.TRAY_NOTIFICATION);
					 } else {
						 
						 getUI().addWindow(new CrearPedidoDeliveryWindow(cliente));
						 
						 close();
					 }
				 }
			});
		}
		return buscarButton;
	}
	
	private Button getNuevoClienteButton() {
		if(nuevoClienteButton == null) {
			nuevoClienteButton = new Button("Nuevo Cliente");
			nuevoClienteButton.addClickListener(new ClickListener() {
				 public void buttonClick(final ClickEvent event) {
					 getUI().addWindow(new ClienteWindow(null));
				 }
			});
		}
		return nuevoClienteButton;
	}
	
	private Button getCancelarButton() {
		if(cancelarButton == null) {
			cancelarButton = new Button("Cerrar");
			cancelarButton.addClickListener(new ClickListener() {
				 public void buttonClick(final ClickEvent event) {
					 close();
				 }
			});
		}
		return cancelarButton;
	}
	
	//------TextField-----------------------------------------------------------------------------
	
	private TextField getTelefonoTextField() {
		if(telefonoField == null) {
			telefonoField = new TextField();
			//telefonoField.setMaxLength(Usuario.NOMBRE_MAX_LENGTH);
			telefonoField.setRequired(true);
		}
		return telefonoField;
	}
	
	//------Label---------------------------------------------------------------------------------
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label();
			headerLabel.setStyleName("h2");
			headerLabel.setValue("Buscar Cliente");
		}
		return headerLabel;
	}
	
	private Label getTelefonoLabel() {
		if(telefonoLabel == null) {
			telefonoLabel = new Label();
			telefonoLabel.setValue("Teléfono:");
		}
		return telefonoLabel;
	}
}
