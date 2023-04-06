package com.mesero.web.window;


import com.mesero.bean.Cliente;
import com.mesero.config.Cripto;
import com.mesero.manageBean.ManageCliente;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ClienteWindow extends Window {

	private VerticalLayout mainLayout = new VerticalLayout();
	
	private HorizontalLayout footerLayout = new HorizontalLayout();
	
	private HorizontalLayout headerLayout = new HorizontalLayout();
	
	private FormLayout formLayout = new FormLayout();
	
	private Label headerLabel = new Label();
	
	private TextField telefonoField = null; 
	private TextField direccionField = null;
	private TextField emailField = null;
	
	private PasswordField actualPasswordField = null;
	private PasswordField passwordField = null; 
	private PasswordField confirmPasswordField = null;
	
	private Button aceptarButton = null;
	private Button cancelarButton = null;
	
	private Cliente cliente;
	
	private ManageCliente manageCliente = new ManageCliente();
	
	public ClienteWindow(Cliente cliente) {
		this.cliente = cliente;
		
		setWidth(440, Sizeable.Unit.PIXELS);
		setHeight(445, Sizeable.Unit.PIXELS);
		//setSizeUndefined();

		formLayout.setSpacing(true);
		formLayout.setMargin(false);
		formLayout.setMargin(new MarginInfo(true,true,true,true));
		
		formLayout.addComponent(getTelefonoTextField());
		formLayout.addComponent(getDireccionTextField());
		formLayout.addComponent(getEmailTextField());
		
		if(this.cliente != null) 
		formLayout.addComponent(getActualPasswordField());
		formLayout.addComponent(getPasswordField());
		formLayout.addComponent(getConfirmPasswordField());
		
		footerLayout.setSpacing(true);
		footerLayout.setMargin(false);
		
		footerLayout.addComponent(getCancelarButton());
		footerLayout.setComponentAlignment(getCancelarButton(), Alignment.MIDDLE_CENTER);
		footerLayout.addComponent(getAceptarButton());
		footerLayout.setComponentAlignment(getAceptarButton(), Alignment.MIDDLE_CENTER);
		footerLayout.setHeight(70,Sizeable.Unit.PIXELS);
		
		headerLabel.setStyleName("h2");
		headerLabel.setValue("Nuevo Cliente");
		headerLayout.addComponent(headerLabel);
		
		Panel centerPanel = new Panel();
		centerPanel.setSizeFull();
		centerPanel.setContent(formLayout);
		
		mainLayout.addComponent(headerLayout);
		mainLayout.setComponentAlignment(headerLayout, Alignment.TOP_CENTER);
		mainLayout.addComponent(centerPanel);
		mainLayout.setComponentAlignment(centerPanel, Alignment.MIDDLE_CENTER);
		mainLayout.addComponent(footerLayout);
		mainLayout.setComponentAlignment(footerLayout, Alignment.BOTTOM_CENTER);
			
		setResizable(false);
		setModal(true);
		setContent(mainLayout);
		
		if(this.cliente != null) {
			setData();
			setHeight(495, Sizeable.Unit.PIXELS);
			headerLabel.setValue("Editar Cliente");
		}
	}
	
	//Fields----------------------------------------------------------------------------------
	
	private TextField getTelefonoTextField() {
		if(telefonoField == null) {
			telefonoField = new TextField("Teléfono");
			//telefonoField.setMaxLength(Usuario.NOMBRE_MAX_LENGTH);
			telefonoField.setRequired(true);
		}
		return telefonoField;
	}
	
	private TextField getDireccionTextField() {
		if(direccionField == null) {
			direccionField = new TextField("Dirección");
			//telefonoField.setMaxLength(Usuario.NOMBRE_MAX_LENGTH);
			direccionField.setRequired(true);
		}
		return direccionField;
	}
	
	private TextField getEmailTextField() {
		if(emailField == null) {
			emailField = new TextField("E-Mail");
			emailField.addValidator(new EmailValidator("Ingrese un E-Mail valido."));
			//telefonoField.setMaxLength(Usuario.NOMBRE_MAX_LENGTH);
			emailField.setRequired(false);
		}
		return emailField;
	}
	
	private PasswordField getActualPasswordField() {
		if(actualPasswordField == null) {
			actualPasswordField = new PasswordField("Actual Password");
			//actualPasswordField.setMaxLength(Usuario.PASSWORD_MAX_LENGTH);
			actualPasswordField.setRequired(true);
			//actualPasswordField.setReadOnly(readOnly);
		}
		return actualPasswordField;
	}
	
	private PasswordField getPasswordField() {
		if(passwordField == null) {
			passwordField = new PasswordField("Password");
			//passwordField.setMaxLength(Usuario.PASSWORD_MAX_LENGTH);
			passwordField.setRequired(true);
			//passwordField.setReadOnly(readOnly);
		}
		return passwordField;
	}
	
	private PasswordField getConfirmPasswordField() {
		if(confirmPasswordField == null) {
			confirmPasswordField = new PasswordField("Confirmar Password");
			//confirmPasswordField.setMaxLength(Usuario.PASSWORD_MAX_LENGTH);
			confirmPasswordField.setRequired(true);
			//confirmPasswordField.setReadOnly(readOnly);
		}
		return confirmPasswordField;
	}
	
	//Buttons----------------------------------------------------------------------------------
	
	private Button getAceptarButton() {
		if(aceptarButton == null) {
			aceptarButton = new Button("Aceptar");
			aceptarButton.addClickListener(new ClickListener() {
				public void buttonClick(final ClickEvent event) {
					validarCrearActualizar();
				}
			});
		}
		return aceptarButton;
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
	
	//Logica----------------------------------------------------------------------------------
	
	private void setData() {
		getTelefonoTextField().setValue(cliente.getTelefono());
		getTelefonoTextField().setReadOnly(true);
		getDireccionTextField().setValue(cliente.getDireccion());
		getEmailTextField().setValue(cliente.getEmail());
	}
	
	private boolean Validar() {
		try {
			getTelefonoTextField().validate();
		} catch (InvalidValueException e) {
		    Notification.show("El valor del Teléfono no puede ser vacio.",Notification.Type.TRAY_NOTIFICATION);
			getTelefonoTextField().setValidationVisible(true);
			return false;
		}
		
		try {
			getDireccionTextField().validate();
		} catch (InvalidValueException e) {
		    Notification.show("El valor de la Dirección no puede ser vacio.",Notification.Type.TRAY_NOTIFICATION);
		    getDireccionTextField().setValidationVisible(true);
		    return false;
		}
		
		try {
			getEmailTextField().validate();
		} catch (InvalidValueException e) {
		    Notification.show("El valor del E-Mail debe ser valido.",Notification.Type.TRAY_NOTIFICATION);
		    getDireccionTextField().setValidationVisible(true);
		    return false;
		}
		
		
		if(cliente != null) {
			try {
				getActualPasswordField().validate();
				if(!Cripto.getInstance().Desencriptar(cliente.getPassword()).equals(getActualPasswordField().getValue())) {
					Notification.show("El password Actual no es correcto.");
					return false;
				}
			} catch (InvalidValueException e) {
			    Notification.show("El valor del Password Actual no puede ser vacio.",Notification.Type.TRAY_NOTIFICATION);
			    getActualPasswordField().setValidationVisible(true);
			    return false;
			}
		}
		
		try {
			getPasswordField().validate();
		} catch (InvalidValueException e) {
		    Notification.show("El valor del Password no puede ser vacio.",Notification.Type.TRAY_NOTIFICATION);
		    getPasswordField().setValidationVisible(true);
		    return false;
		}
		
		try {
			getConfirmPasswordField().validate();
		} catch (InvalidValueException e) {
		    Notification.show("El valor de la Confirmacion del Password no puede ser vacio.",Notification.Type.TRAY_NOTIFICATION);
		    getConfirmPasswordField().setValidationVisible(true);
		    return false;
		}
		
		if(!getConfirmPasswordField().getValue().equals(getPasswordField().getValue())) {
			Notification.show("El valor del Password y su Confirmacion deben coincidir.",Notification.Type.TRAY_NOTIFICATION);
			return false;
		}
		
		return true;
	}
	
	private void validarCrearActualizar() {
		if(Validar()) {
			if(getTelefonoTextField().isValid() &&
			   getDireccionTextField().isValid() &&
			   getEmailTextField().isValid() &&
			   getPasswordField().isValid()) {
			
				if(cliente != null) {
					cliente.setDireccion(getDireccionTextField().getValue());
					cliente.setEmail(getEmailTextField().getValue());
					cliente.setPassword(Cripto.Encriptar(getPasswordField().getValue()));
					manageCliente.saveOrUpdateCliente(cliente);
					close();
			 	} else {

					cliente = manageCliente.getCliente(getTelefonoTextField().getValue());
					
					if(cliente != null) {
						Notification.show("Ya existe un Cliente con igual Teléfono.",Notification.Type.TRAY_NOTIFICATION);
						cliente = null;
					} else {
						cliente = new Cliente();
						cliente.setTelefono(getTelefonoTextField().getValue());
						cliente.setDireccion(getDireccionTextField().getValue());
						cliente.setEmail(getEmailTextField().getValue());
						cliente.setPassword(Cripto.Encriptar(getPasswordField().getValue()));
						manageCliente.saveCliente(cliente);
						close();
					}
					
			 	}
			}
		}
	}
}
