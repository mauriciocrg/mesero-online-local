package com.mesero.web.window;

import java.io.File;

import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.core.Status;
import com.mesero.web.component.NumberChooser;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MenuItemPedidoWindow extends Window {
	
	private Pedido pedido;
	private MenuItem menuItem;
	private PedidoItem pedidoItem;
	
	private TextField nombreField;
	private TextField ingredientesField;
	private TextField precioField;
	
	private Button okButton;
	private Button cancelButton;
	private NumberChooser cantidadChooser;
	
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
	
	public MenuItemPedidoWindow(Pedido pedido, MenuItem menuItem) {
		this.pedido = pedido;
		this.menuItem = menuItem;
		
		setModal(true);
		setClosable(false);
		setResizable(false);
		
		setContent(getContentLayout());
		
		setWidth(310, Sizeable.Unit.PIXELS);
	}
	
	public MenuItemPedidoWindow(PedidoItem pedidoItem) {
		this.pedidoItem = pedidoItem;
		this.menuItem = pedidoItem.getMenuItem();
		
		setModal(true);
		setClosable(false);
		setResizable(false);
		
		setContent(getContentLayout());
		
		setWidth(310, Sizeable.Unit.PIXELS);
	}
	
	//----Layout----------------------------------------------------------------------------------------------
	
	private VerticalLayout getContentLayout() {
		if(contentLayout == null) {
			contentLayout = new VerticalLayout();
			contentLayout.setSpacing(true);
			contentLayout.setMargin(false);
			/*if(width > height) {
				contentLayout.addComponent(getContentHorizontalLayout());
			} else {*/
			contentLayout.addComponent(getContentVerticalLayout());
			//}
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
			nombreLayout.setCaption("<span style='text-align:center;'>Menu:</span>");
			nombreLayout.setStyleName("v-border", true);
			nombreLayout.addComponent(getNombreField());
		}
		return nombreLayout;
	}
	
	private HorizontalLayout getIngredientesLayout() {
		if(ingredientesLayout == null) {
			ingredientesLayout = new HorizontalLayout();
			ingredientesLayout.setCaptionAsHtml(true);
			ingredientesLayout.setCaption("<span style='text-align:center;'>Ingredientes:</span>");
			ingredientesLayout.setStyleName("v-border", true);
			ingredientesLayout.addComponent(getIngredientesField());
		}
		return ingredientesLayout;
	}
	
	private HorizontalLayout getPrecioLayout() {
		if(precioLayout == null) {
			precioLayout = new HorizontalLayout();
			precioLayout.setCaptionAsHtml(true);
			precioLayout.setCaption("<span style='text-align:center;'>Precio: $</span>");
			precioLayout.setStyleName("v-border", true);
			precioLayout.addComponent(getPrecioField());
		}
		return precioLayout;
	}
	
	private Panel getImagePanel() {
		if(imagePanel == null) {
			imagePanel = new Panel();
			
			/*int panelWidth = 0;
			int panelHeight = 0;
			
			if(width < height) {
				panelWidth = width - 20;
				panelHeight = width * 50 / 100;
			} else {
				panelWidth = height - 20;
				panelHeight = height * 50 / 100;
			}
			*/
			imagePanel.setWidth(300,Sizeable.Unit.PIXELS);
			imagePanel.setHeight(180,Sizeable.Unit.PIXELS);
			
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
	
	private TextField getNombreField() {
		if(nombreField == null) {
			nombreField = new TextField();
			nombreField.setValue(menuItem.getNombre_menuItem());
			nombreField.setReadOnly(false);
		}
		return nombreField;
	}
	
	private TextField getPrecioField() {
		if(precioField == null) {
			precioField = new TextField();
			precioField.setValue(""+menuItem.getPrecio());
			precioField.setReadOnly(false);
		}
		return precioField;
	}
	
	private TextField getIngredientesField() {
		if(ingredientesField == null) {
			ingredientesField = new TextField();
			ingredientesField.setValue(menuItem.getIngredientes().toString());
			ingredientesField.setReadOnly(true);
		}
		return ingredientesField;
	}
	
	//----Buttons---------------------------------------------------------------------------------------------
	
	private Button getOkButton() {
		if(okButton == null) {
			okButton = new Button("Ok", FontAwesome.CHECK);
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
			okButton.setWidth(120,Sizeable.Unit.PIXELS);
		}
		return okButton;
	}
	
	private Button getCancelButton() {
		if(cancelButton == null) {
			cancelButton = new Button("Cancelar", FontAwesome.TIMES);
			cancelButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					close();
				}
			});
			cancelButton.setWidth(120,Sizeable.Unit.PIXELS);
		}
		return cancelButton;
	}
	
	//----Chooser----------------------------------------------------------------------------------------------
	
	private NumberChooser getCantidadChooser() {
		if(cantidadChooser == null) {
			cantidadChooser = new NumberChooser("Cantidad: ");
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
