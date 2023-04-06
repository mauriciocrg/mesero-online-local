package com.mesero.web.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NumberChooser extends HorizontalLayout {
	
	private Button masButton;
	private Button menosButton;
	private Label label;
	
	private final int MAX_VALUE = 100;
	private final int MIN_VALUE = 1;
	
	private int value = 1;
	
	public NumberChooser(String caption) {
		
		setSpacing(true);
		setMargin(false);
		
		setCaptionAsHtml(true);
		setCaption("<span style='text-align:center;'>"+caption+"</span>");
		
		setStyleName("v-border", true);
		
		addComponent(getLabel());		
		addComponent(getMasButton());
		addComponent(getMenosButton());
		setLabelValue();
	}
	
	public Button getMasButton() {
		if(masButton == null) {
			masButton = new Button(FontAwesome.PLUS);
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
	
	public Button getMenosButton() {
		if(menosButton == null) {
			menosButton = new Button(FontAwesome.MINUS);
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
		getLabel().setCaption("<span style='text-align:center;border:2px solid #000;display:block;width:50px';>"+value+"</span>");
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		setLabelValue();
	}

}
