package com.mesero.mobil.component;

import com.vaadin.ui.TextField;

public class TextFieldMobilComun extends TextField {

	public TextFieldMobilComun(String value,String caption,int fontSize) {
		setStyleName("v-fontSize", true);
		setCaptionAsHtml(true);
		setValue(value);
		if(caption != null) setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>"+caption+"</span>");
	}
}
