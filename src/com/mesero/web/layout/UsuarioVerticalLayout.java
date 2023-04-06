package com.mesero.web.layout;

import com.mesero.config.Config;
import com.mesero.config.Cripto;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

public class UsuarioVerticalLayout extends VerticalLayout {

	private FormLayout formLayout = null;
	private PasswordField currentPassword = null;
	private PasswordField newPassword = null;
	private PasswordField confirmNewPassword = null;
	private Button aceptarButton = null;
	private Label label = null;
	
	public UsuarioVerticalLayout() {
		setCaption("Usuario");
		setIcon(FontAwesome.USER);
		setSpacing(true);
		setMargin(true);
		setWidth(100, Sizeable.Unit.PERCENTAGE);
		setHeight(100, Sizeable.Unit.PERCENTAGE);
		
		GridLayout gridLayout = new GridLayout();
        gridLayout.addStyleName("outlined");
        gridLayout.setSizeFull();

        gridLayout.removeAllComponents();

        gridLayout.setRows(3);
        gridLayout.setColumns(3);
		
        gridLayout.addComponent(getFormLayout(), 1, 1);
        
		addComponent(gridLayout);
		setComponentAlignment(gridLayout,Alignment.MIDDLE_CENTER);
	}
	
	private FormLayout getFormLayout() {
		if(formLayout == null) {
			formLayout = new FormLayout();
			formLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			formLayout.addComponents(getLabel(),getCurrentPassword(),getNewPassword(),getConfirmNewPassword(),getAceptarButton());
		}
		return formLayout;
	}
	
	private PasswordField getCurrentPassword() {
		if(currentPassword == null) {
			currentPassword = new PasswordField("Password Actual");
			currentPassword.setWidth(150, Sizeable.Unit.PIXELS);
		}
		return currentPassword;
	}
	
	private PasswordField getNewPassword() {
		if(newPassword == null) {
			newPassword = new PasswordField("Password Nueva");
			newPassword.setWidth(150, Sizeable.Unit.PIXELS);
		}
		return newPassword;
	}
	
	private PasswordField getConfirmNewPassword() {
		if(confirmNewPassword == null) {
			confirmNewPassword = new PasswordField("Confirmar Password Nueva");
			confirmNewPassword.setWidth(150, Sizeable.Unit.PIXELS);
		}
		return confirmNewPassword;
	}
	
	private Button getAceptarButton() {
		if(aceptarButton == null) {
			aceptarButton = new Button("Aceptar");
			aceptarButton.setWidth(150, Sizeable.Unit.PIXELS);
			aceptarButton.addClickListener(new ClickListener(){

				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					if(Cripto.Desencriptar(Config.getInstance().getProperti(Config.PASSWORD)).equals(getCurrentPassword().getValue())) {
					
						if(getNewPassword().getValue().equals(getConfirmNewPassword().getValue())) {
							Config.getInstance().setProperti(Config.PASSWORD, Cripto.Encriptar(getNewPassword().getValue()));
							Notification.show("El Password ha sido actualizado correctamente.", Type.TRAY_NOTIFICATION);
						} else {
							Notification.show("El Password nuevo debe ser igual a la Confirmacion del Password.", Type.TRAY_NOTIFICATION);
						}
						
					} else {
						Notification.show("El Password actual es incorrecta.", Type.TRAY_NOTIFICATION);
					}
				}
				
			});
		}
		return aceptarButton;
	}
	
	private Label getLabel() {
		if(label == null) {
			label = new Label("Actualizacion del Password");
			label.setStyleName("h2");
		}
		return label;
	}
}
