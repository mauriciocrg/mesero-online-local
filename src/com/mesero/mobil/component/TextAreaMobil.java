package com.mesero.mobil.component;

import com.vaadin.ui.TextArea;

public class TextAreaMobil extends TextArea {

	public TextAreaMobil(String caption,int fontSize) {
		setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>"+caption+"</span>");
		setCaptionAsHtml(true);
		setStyleName("v-fontSize", true);
	}
}
