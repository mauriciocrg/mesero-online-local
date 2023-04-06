package com.mesero.mobil.component;


import com.mesero.core.Status;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;

public class ButtonMobil extends Button {
	
	private FontAwesome icon;
	private String caption;
	
	public ButtonMobil(String caption,FontAwesome icon,int fontSize,int buttonSize) {
		super("<span style='font-size:"+fontSize+"px;'>"+caption+"</span>  "+Status.getInstance().getIconAsHtml("",icon,fontSize));
        this.icon = icon; 
        this.caption = caption;
		setCaptionAsHtml(true);
        //setWidth(buttonSize+(fontSize*caption.length()), Sizeable.Unit.PIXELS);
        setHeight(buttonSize, Sizeable.Unit.PIXELS);
	}

	public void update(int fontSize,int buttonSize) {
		setCaption("<span style='font-size:"+fontSize+"px;'>"+caption+"</span>"+Status.getInstance().getIconAsHtml("",icon,fontSize));
		setWidth(buttonSize+(fontSize*caption.length()), Sizeable.Unit.PIXELS);
        setHeight(buttonSize, Sizeable.Unit.PIXELS);
	}
}
