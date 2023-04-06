package com.mesero.mobil.component;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.NativeSelect;

public class NumberComboBoxMobil extends NativeSelect {

	private int maxValue = 100;
	private int minValue = 1;
	
	private int value = 1;
	
	public NumberComboBoxMobil(int fontSize,int buttonSize) {
		//setCaption("<span style='font-size:"+fontSize+"px;'>"+minValue+"</span>");
		setStyleName("v-fontSize",true);
		setHeight(buttonSize, Sizeable.Unit.PIXELS);
		setWidth(buttonSize*3, Sizeable.Unit.PIXELS);
		//setCaptionAsHtml(true);
		for(int i = minValue; i < maxValue; i++) addItem(i);
		setValue(value);
	}
	
	public void update(int fontSize, int buttonSize) {
		//setCaption("<span style='font-size:"+fontSize+"px;'>"+value+"</span>");
		setStyleName("v-fontSize",true);
		setHeight(buttonSize, Sizeable.Unit.PIXELS);
		setWidth(buttonSize*3, Sizeable.Unit.PIXELS);
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
}
