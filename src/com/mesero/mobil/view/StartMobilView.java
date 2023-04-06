package com.mesero.mobil.view;


import com.mesero.bean.Pedido;
import com.mesero.mobil.component.ButtonMobil;
import com.mesero.mobil.component.IconButtonMobil;
import com.mesero.mobil.component.LabelMobil;
import com.mesero.mobil.component.NumberChooserMobil;
import com.mesero.mobil.component.NumberComboBoxMobil;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class StartMobilView extends VerticalLayout implements View {

	private int width = 0;
	private int height = 0;
	
	private int buttonSize = 0;
	private int fontSize = 0;

	private Navigator navigator;
	
	private NumberChooserMobil numberChooser;
	private NumberComboBoxMobil numberComboBox;
	private ButtonMobil okButton;
	private IconButtonMobil iconButton;
	private LabelMobil label;
	
	private MainMobilView mainMobilView;

	public StartMobilView(Navigator navigator,MainMobilView mainMobilView) {
		setSizeFull();
		this.navigator = navigator;
		this.mainMobilView = mainMobilView;
		
		width = navigator.getUI().getPage().getBrowserWindowWidth();
		height = navigator.getUI().getPage().getBrowserWindowHeight();
		
		setSizes();
		
		removeAllComponents();
		
		VerticalLayout contentLayout = new VerticalLayout();
		
		contentLayout.addComponents(getLabel(),getNumberComboBox(),getOkButton()/*,getIconButton1()*/);
		contentLayout.setComponentAlignment(getLabel(), Alignment.BOTTOM_CENTER);
		contentLayout.setComponentAlignment(getNumberComboBox(), Alignment.BOTTOM_CENTER);
		contentLayout.setComponentAlignment(getOkButton(), Alignment.BOTTOM_CENTER);
		//contentLayout.setComponentAlignment(getIconButton1(), Alignment.BOTTOM_CENTER);
		contentLayout.setMargin(true);
		contentLayout.setSpacing(true);
		
		
		addComponent(contentLayout);
		setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
	}
	
	private void setSizes() {
		buttonSize = (Math.min(width,height)*15)/100;
        fontSize = (buttonSize*67)/100;
        //navigator.getUI().getPage().getJavaScript().getCurrent().execute("changeCss('.v-fontSize','font-size: " + String.valueOf(fontSize) + "px;');");
        navigator.getUI().getPage().getCurrent().getStyles().add(".v-fontSize { font-size: "+String.valueOf(fontSize-4)+"px; }");
	}
	
	public void updateComponents(int width, int height) {
		this.width = width;
		this.height = height;
		setSizes();
		getLabel().update("N° De Mesa:",fontSize);
		getNumberComboBox().update(fontSize, buttonSize);
		getOkButton().update(fontSize, buttonSize);
		//getIconButton1().update(fontSize, buttonSize);
	}
	/*
	private NumberChooserMobil getNumberChooser() {
		if(numberChooser == null) {
			numberChooser = new NumberChooserMobil(fontSize, buttonSize);
		}
		return numberChooser;
	}*/
	
	private NumberComboBoxMobil getNumberComboBox() {
		if(numberComboBox == null) {
			numberComboBox = new NumberComboBoxMobil(fontSize, buttonSize);
		}
		return numberComboBox;
	}
	
	private ButtonMobil getOkButton() {
		if(okButton == null) {
			okButton = new ButtonMobil("OK",FontAwesome.CHECK,fontSize,buttonSize);
			okButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Pedido pedido = new Pedido();
					pedido.setNumeroMesa(((Integer)getNumberComboBox().getValue()).intValue());
					mainMobilView.setPedido(pedido);
					mainMobilView.updateComponents(width, height);
					navigator.navigateTo("main");
				}
			});
		}
		return okButton;
	}
	
	private IconButtonMobil getIconButton1() {
		if(iconButton == null) {
			iconButton = new IconButtonMobil(FontAwesome.CHECK,fontSize,buttonSize);
			
		}
		return iconButton;
	}
	
	
	private LabelMobil getLabel() {
		if(label == null) {
			label = new LabelMobil("N° De Mesa:",fontSize);
			label.setWidth(buttonSize,Sizeable.Unit.PIXELS);
		}
		return label;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		for(Window window : getUI().getWindows()) {
    		window.close();
    	}
	}
}
