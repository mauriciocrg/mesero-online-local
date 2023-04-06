package com.mesero.mobil.component;

import com.mesero.core.Status;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;

public class IconButtonMobil extends Button {

	private FontAwesome icon;
	
	public IconButtonMobil(FontAwesome icon,int fontSize,int buttonSize) {
		super(Status.getInstance().getIconAsHtml("",icon,fontSize-5));
		//System.out.println(Status.getInstance().getIconAsHtml(icon,fontSize));
		this.icon = icon; 
		setCaptionAsHtml(true);
        setWidth(buttonSize, Sizeable.Unit.PIXELS);
        setHeight(buttonSize, Sizeable.Unit.PIXELS);
	}
	
	public void update(int fontSize,int buttonSize) {
		setCaption(Status.getInstance().getIconAsHtml("",icon,fontSize-5));
		setWidth(buttonSize, Sizeable.Unit.PIXELS);
        setHeight(buttonSize, Sizeable.Unit.PIXELS);
	}
	
}
