package com.mesero.mobil.component;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Label;

public class LabelMobil extends Label {
	
	public LabelMobil(String caption,int fontSize) {
		//setWidth(100,Sizeable.Unit.PERCENTAGE);
		setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>"+caption+"</span>");
		setCaptionAsHtml(true);
	}
	
	public void update(String caption,int fontSize) {
		//setWidth(100,Sizeable.Unit.PERCENTAGE);
		setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>"+caption+"</span>");
		setWidth(fontSize,Sizeable.Unit.PIXELS);
	}
}
