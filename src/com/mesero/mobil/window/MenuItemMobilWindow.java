package com.mesero.mobil.window;

import java.io.File;


import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.core.Status;
import com.mesero.mobil.component.ButtonMobil;
import com.mesero.mobil.component.NumberChooserMobil;
import com.mesero.mobil.component.TextFieldMobilComun;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MenuItemMobilWindow extends Window {

	private Pedido pedido;
	private MenuItem menuItem;
	private PedidoItem pedidoItem;
	private int width;
	private int height;
	private int fontSize;
	private int buttonSize;
	
	private TextFieldMobilComun nombreField;
	private TextFieldMobilComun ingredientesField;
	private TextFieldMobilComun precioField;
	
	private ButtonMobil okButton;
	private ButtonMobil cancelButton;
	private NumberChooserMobil cantidadChooser;
	
	private FormLayout valuesLayout;
	private VerticalLayout imgLayout;
	private VerticalLayout contentLayout;
	private VerticalLayout contentVerticalLayout;
	private HorizontalLayout nombreLayout;
	private HorizontalLayout ingredientesLayout;
	private HorizontalLayout precioLayout;
	private HorizontalLayout contentHorizontalLayout;
	private HorizontalLayout footerLayout;
	
	private Panel imagePanel;
	
	public MenuItemMobilWindow(Pedido pedido, MenuItem menuItem, int width, int height,int fontSize, int buttonSize) {
		this.pedido = pedido;
		this.menuItem = menuItem;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize-5;
		this.buttonSize = buttonSize-5;
		
		setModal(true);
		setClosable(false);
		setResizable(false);
		
		setContent(getContentLayout());
		
		if(width < height) {
			setWidth(width-10,Sizeable.Unit.PIXELS);
		}
	}
	
	public MenuItemMobilWindow(PedidoItem pedidoItem, int width, int height,int fontSize, int buttonSize) {
		this.pedidoItem = pedidoItem;
		this.menuItem = pedidoItem.getMenuItem();
		this.width = width;
		this.height = height;
		this.fontSize = fontSize-5;
		this.buttonSize = buttonSize-5;
		
		setModal(true);
		setClosable(false);
		setResizable(false);
		
		setContent(getContentLayout());
		
		if(width < height) {
			setWidth(width-10,Sizeable.Unit.PIXELS);
		}
	}
	
	//----Layout----------------------------------------------------------------------------------------------
	
	private VerticalLayout getContentLayout() {
		if(contentLayout == null) {
			contentLayout = new VerticalLayout();
			contentLayout.setSpacing(true);
			contentLayout.setMargin(false);
			if(width > height) {
				contentLayout.addComponent(getContentHorizontalLayout());
			} else {
				contentLayout.addComponent(getContentVerticalLayout());
			}
			contentLayout.addComponent(getFooterLayout());
			contentLayout.setStyleName("v-border");
		}
		return contentLayout;
	}
	
	private VerticalLayout getContentVerticalLayout() {
		if(contentVerticalLayout == null) {
			contentVerticalLayout = new VerticalLayout();
			contentVerticalLayout.setSpacing(true);
			contentVerticalLayout.setMargin(false);
			contentVerticalLayout.addComponents(getImagePanel(),getValuesLayout());
		}
		return contentVerticalLayout;
	}
	
	private HorizontalLayout getContentHorizontalLayout() {
		if(contentHorizontalLayout == null) {
			contentHorizontalLayout = new HorizontalLayout();
			contentHorizontalLayout.setSpacing(true);
			contentHorizontalLayout.setMargin(false);
			contentHorizontalLayout.addComponents(getImagePanel(),getValuesLayout());
		}
		return contentHorizontalLayout;
	}
	
	private HorizontalLayout getFooterLayout() {
		if(footerLayout == null) {
			footerLayout = new HorizontalLayout();
			footerLayout.setSpacing(true);
			footerLayout.setMargin(false);
			footerLayout.setStyleName("v-border", true);
			footerLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			footerLayout.addComponents(getCancelButton(),getOkButton());
			footerLayout.setComponentAlignment(getCancelButton(), Alignment.MIDDLE_CENTER);
			footerLayout.setComponentAlignment(getOkButton(), Alignment.MIDDLE_CENTER);
		}
		return footerLayout;
	}
	
	private FormLayout getValuesLayout() {
		if(valuesLayout == null) {
			valuesLayout = new FormLayout();
			valuesLayout.setMargin(false);
			valuesLayout.setSpacing(false);
			valuesLayout.addComponents(getNombreLayout(),getIngredientesLayout(),getPrecioLayout(),getCantidadChooser());
		}
		return valuesLayout;
	}
	
	private VerticalLayout getImgLayout() {
		if(imgLayout == null) {
			imgLayout = new VerticalLayout();
			imgLayout.setMargin(false);
			imgLayout.setSpacing(true);
			imgLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			imgLayout.setHeight(100,Sizeable.Unit.PERCENTAGE);
			
		}
		return imgLayout;
	}
	
	private HorizontalLayout getNombreLayout() {
		if(nombreLayout == null) {
			nombreLayout = new HorizontalLayout();
			nombreLayout.setCaptionAsHtml(true);
			nombreLayout.setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>Menu:</span>");
			nombreLayout.setStyleName("v-border", true);
			nombreLayout.addComponent(getNombreField());
		}
		return nombreLayout;
	}
	
	private HorizontalLayout getIngredientesLayout() {
		if(ingredientesLayout == null) {
			ingredientesLayout = new HorizontalLayout();
			ingredientesLayout.setCaptionAsHtml(true);
			ingredientesLayout.setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>Ingredientes:</span>");
			ingredientesLayout.setStyleName("v-border", true);
			ingredientesLayout.addComponent(getIngredientesField());
		}
		return ingredientesLayout;
	}
	
	private HorizontalLayout getPrecioLayout() {
		if(precioLayout == null) {
			precioLayout = new HorizontalLayout();
			precioLayout.setCaptionAsHtml(true);
			precioLayout.setCaption("<span style='text-align:center;font-size:"+fontSize+"px;'>Precio: $</span>");
			precioLayout.setStyleName("v-border", true);
			precioLayout.addComponent(getPrecioField());
		}
		return precioLayout;
	}
	
	private Panel getImagePanel() {
		if(imagePanel == null) {
			imagePanel = new Panel();
			
			int panelWidth = 0;
			int panelHeight = 0;
			
			if(width < height) {
				panelWidth = width - 20;
				panelHeight = width * 50 / 100;
			} else {
				panelWidth = height - 20;
				panelHeight = height * 50 / 100;
			}
			
			imagePanel.setWidth(panelWidth,Sizeable.Unit.PIXELS);
			imagePanel.setHeight(panelHeight,Sizeable.Unit.PIXELS);
			
			File imgFile = new File(Status.getInstance().basePath+File.separator+"images"+File.separator+menuItem.getImageName());
			
			if(!imgFile.exists()) {	
				imgFile = new File(Status.getInstance().basePath+File.separator+"images"+File.separator+"default.img");
			}	
			
			Image img = new Image(null,new FileResource(imgFile));
			img.setWidth(100, Sizeable.Unit.PERCENTAGE);
			img.setHeight(100, Sizeable.Unit.PERCENTAGE);
			getImgLayout().removeAllComponents();
			getImgLayout().addComponent(img);
			getImgLayout().setComponentAlignment(img, Alignment.MIDDLE_CENTER);
			
			imagePanel.setContent(getImgLayout());
		}
		return imagePanel;
	}
	
	//----Labels----------------------------------------------------------------------------------------------
	
	private TextFieldMobilComun getNombreField() {
		if(nombreField == null) {
			nombreField = new TextFieldMobilComun(menuItem.getNombre_menuItem(),null,fontSize);
			nombreField.setEnabled(false);
		}
		return nombreField;
	}
	
	private TextFieldMobilComun getPrecioField() {
		if(precioField == null) {
			precioField = new TextFieldMobilComun(""+menuItem.getPrecio(),null,fontSize);
			precioField.setEnabled(false);
		}
		return precioField;
	}
	
	private TextFieldMobilComun getIngredientesField() {
		if(ingredientesField == null) {
			ingredientesField = new TextFieldMobilComun(menuItem.getIngredientes().toString(),null,fontSize);
			ingredientesField.setEnabled(false);
		}
		return ingredientesField;
	}
	
	//----Buttons---------------------------------------------------------------------------------------------
	
	private ButtonMobil getOkButton() {
		if(okButton == null) {
			okButton = new ButtonMobil("Ok", FontAwesome.CHECK, fontSize, buttonSize);
			okButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					if(pedidoItem == null) {
						PedidoItem pedidoItem = getPedidoItem(menuItem);
						
						if(pedidoItem != null) {
							pedidoItem.setCantidad(pedidoItem.getCantidad()+getCantidadChooser().getValue());
						} else {
							pedidoItem = new PedidoItem();
							pedidoItem.setCantidad(getCantidadChooser().getValue());
							pedidoItem.setMenuItem(menuItem);
							pedidoItem.setHay(1);
							pedido.getItems().add(pedidoItem);
						}
					} else {
						pedidoItem.setCantidad(getCantidadChooser().getValue());
					}
					close();
				}
			});
			okButton.setWidth((Math.min(width, height)/2)-30,Sizeable.Unit.PIXELS);
		}
		return okButton;
	}
	
	private ButtonMobil getCancelButton() {
		if(cancelButton == null) {
			cancelButton = new ButtonMobil("Cancelar", FontAwesome.TIMES, fontSize, buttonSize);
			cancelButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					close();
				}
			});
			cancelButton.setWidth((Math.min(width, height)/2)-30,Sizeable.Unit.PIXELS);
		}
		return cancelButton;
	}
	
	//----Chooser----------------------------------------------------------------------------------------------
	
	private NumberChooserMobil getCantidadChooser() {
		if(cantidadChooser == null) {
			cantidadChooser = new NumberChooserMobil("Cantidad: ",fontSize, buttonSize);
			if(pedidoItem != null) cantidadChooser.setValue(pedidoItem.getCantidad());
			cantidadChooser.getMasButton().addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					getPrecioField().setValue(""+menuItem.getPrecio()*cantidadChooser.getValue());		
				}
			});
			cantidadChooser.getMenosButton().addClickListener(new ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					getPrecioField().setValue(""+menuItem.getPrecio()*cantidadChooser.getValue());		
				}
			});
			
		}
		return cantidadChooser;
	}
	
	//----Logica----------------------------------------------------------------------------------------------
	
	private PedidoItem getPedidoItem(MenuItem menuItem) {
		for(PedidoItem pedidoItem : pedido.getItems()) {
			if(pedidoItem.getMenuItem() == menuItem) return pedidoItem;
		}
		return null;
	}
	
}
