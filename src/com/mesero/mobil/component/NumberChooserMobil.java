package com.mesero.mobil.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class NumberChooserMobil extends HorizontalLayout {

	private int fontSize;
	private int buttonSize;
	
	private IconButtonMobil masButton;
	private IconButtonMobil menosButton;
	private Label label;
	
	private final int MAX_VALUE = 100;
	private final int MIN_VALUE = 1;
	
	private int value = 1;
	
	

	public NumberChooserMobil(String caption,int fontSize,int buttonSize) {
		this.fontSize = fontSize;
		this.buttonSize = buttonSize;
		
		setSpacing(true);
		setMargin(false);
		
		setCaptionAsHtml(true);
		setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>"+caption+"</span>");
		
		setStyleName("v-border", true);
		
		addComponent(getLabel());		
		addComponent(getMasButton());
		addComponent(getMenosButton());
		setLabelValue();
	}
	
	public void update(int fontSize,int buttonSize) {
		this.fontSize = fontSize;
		this.buttonSize = buttonSize;
		
		setLabelValue();
		getMasButton().update(fontSize, buttonSize);
		getMenosButton().update(fontSize, buttonSize);
	}
	
	public IconButtonMobil getMasButton() {
		if(masButton == null) {
			masButton = new IconButtonMobil(FontAwesome.PLUS, fontSize, buttonSize);
			masButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					if(value < MAX_VALUE) {
						value ++;
						setLabelValue();
					}
				}
	        });
		}
        return masButton;
	}
	
	public IconButtonMobil getMenosButton() {
		if(menosButton == null) {
			menosButton = new IconButtonMobil(FontAwesome.MINUS, fontSize, buttonSize);
			menosButton.addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					if(value > MIN_VALUE) {
						value --;
						setLabelValue();
					}
				}
	        });
		}
        return menosButton;
	}
	
	private Label getLabel() {
		if(label == null) {
			label = new Label();
			label.setCaptionAsHtml(true);
		}
		return label;
	}
	
	private void setLabelValue() {
		//System.out.println("<span style='font-size:"+fontSize+"px;border:2px solid #000;display:block;width:"+buttonSize*150/100+"px'>"+value+"</span>");
		getLabel().setCaption("<span style='text-align:center;font-size:"+fontSize+"px;border:2px solid #000;display:block;width:"+buttonSize*150/100+"px';>"+value+"</span>");
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		setLabelValue();
	}
}
