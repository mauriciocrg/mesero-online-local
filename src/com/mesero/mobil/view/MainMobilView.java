package com.mesero.mobil.view;


import com.mesero.bean.Menu;
import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.manageBean.ManageMenu;
import com.mesero.mobil.component.IconButtonMobil;
import com.mesero.mobil.component.LabelMobil;
import com.mesero.mobil.layout.MenuItemLayoutMobil;
import com.mesero.mobil.layout.MenuLayoutMobil;
import com.mesero.mobil.layout.PedidoItemLayoutMobil;
import com.mesero.mobil.window.MenuMobilWindow;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MainMobilView extends VerticalLayout implements View {

	private int width = 0;
	private int height = 0;
	
	private int buttonSize = 0;
	private int fontSize = 0;
	
	private Navigator navigator;
	
	private HorizontalLayout headerLayout;
	private HorizontalLayout footerLayout;
	private VerticalLayout contentLayout;
	private VerticalLayout panelLayout;
	private VerticalLayout pedidoLayout;
	private Panel centerPanel;
	
	private IconButtonMobil menuButton;
	private IconButtonMobil cartButton;
	private IconButtonMobil listMenuButton;
	
	private LabelMobil mesaLabel;
	private LabelMobil totalLabel;
	
	
	private Pedido pedido;
	//private ConfirmarPedidoMobilView confirmarPedidoMobilView;
	
	private ManageMenu manageMenu = new ManageMenu();
	
	public MainMobilView(Navigator navigator/*, ConfirmarPedidoMobilView confirmarPedidoMobilView*/) {
		this.navigator = navigator;
		//this.confirmarPedidoMobilView = confirmarPedidoMobilView;
		
		width = navigator.getUI().getPage().getBrowserWindowWidth();
		height = navigator.getUI().getPage().getBrowserWindowHeight();
		
		setSizes();
		
		removeAllComponents();
		
		addComponent(getContentLayout());
		setWidth(100,Sizeable.Unit.PERCENTAGE);
		setHeight(100,Sizeable.Unit.PERCENTAGE);
	}
	
	private void setSizes() {
		buttonSize = (Math.min(width,height)*15)/100;
        fontSize = (buttonSize*67)/100;
        //navigator.getUI().getPage().getJavaScript().getCurrent().execute("changeCss('.v-fontSize','font-size: " + String.valueOf(fontSize) + "px;');");
        navigator.getUI().getPage().getCurrent().getStyles().add(".v-fontSize { font-size: "+String.valueOf(fontSize-4)+"px; }");
	}
	
	public void updateComponents(int width, int height) {
		this.width = width;
		this.height = height;
		setSizes();
		
		headerLayout = null;
		footerLayout = null;
		contentLayout = null;
		panelLayout = null;
		pedidoLayout = null;
		centerPanel = null;
		
		getMenuButton().update(fontSize, buttonSize);
		getCartButton().update(fontSize, buttonSize);
		getListMenuButton().update(fontSize, buttonSize);
		getMesaLabel().update("Mesa N°: "+(pedido != null?""+pedido.getNumeroMesa():""),fontSize);
		getTotalLabel().update("Total: $"+getTotal(), fontSize);
		
		removeAllComponents();
		
		addComponent(getContentLayout());
	}
	
	//---Layouts---------------------------------------------------------------------------------------------------------------------------------
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.setHeight(buttonSize+10,Sizeable.Unit.PIXELS);
			headerLayout.setWidth(width-100,Sizeable.Unit.PIXELS);
			headerLayout.setSpacing(true);
			headerLayout.addComponents(getMenuButton(), getMesaLabel());
			headerLayout.setComponentAlignment(getMenuButton(), Alignment.MIDDLE_LEFT);
			headerLayout.setComponentAlignment(getMesaLabel(), Alignment.MIDDLE_RIGHT);
			headerLayout.setStyleName("v-border", true);
		}
		return headerLayout;
	} 
	
	private HorizontalLayout getFooterLayout() {
		if(footerLayout == null) {
			footerLayout = new HorizontalLayout();
			footerLayout.setHeight(buttonSize+10,Sizeable.Unit.PIXELS);
			footerLayout.setWidth(width-100,Sizeable.Unit.PIXELS);
			footerLayout.setSpacing(true);
			footerLayout.addComponents(getCartButton(),getListMenuButton(),getTotalLabel());
			footerLayout.setComponentAlignment(getCartButton(), Alignment.MIDDLE_LEFT);
			footerLayout.setComponentAlignment(getListMenuButton(), Alignment.MIDDLE_LEFT);
			footerLayout.setComponentAlignment(getTotalLabel(), Alignment.MIDDLE_RIGHT);
			footerLayout.setStyleName("v-border", true);
		}
		return footerLayout;
	} 
	
	private VerticalLayout getContentLayout() {
		if(contentLayout == null) {
			contentLayout = new VerticalLayout();
			contentLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			contentLayout.setHeight(100,Sizeable.Unit.PERCENTAGE);
			contentLayout.addComponents(getHeaderLayout(),getCenterPanel(),getFooterLayout());
			contentLayout.setExpandRatio(getCenterPanel(), 1);
		}
		return contentLayout;
	} 
	
	private VerticalLayout getPanelLayout() {
		if(panelLayout == null) {
			panelLayout = new VerticalLayout();
			panelLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			
			for(Menu menu : manageMenu.listMenu()) {
				panelLayout.addComponent(new MenuLayoutMobil(menu, fontSize, buttonSize));
				
				for(MenuItem menuItem : menu.getItems()){
					if(menuItem.isHay() == 1) panelLayout.addComponent(new MenuItemLayoutMobil(pedido, menuItem, width, height, fontSize, buttonSize));
				}
			}
		}
		return panelLayout;
	} 
	
	private VerticalLayout getPedidoLayout() {
		if(pedidoLayout == null) {
			pedidoLayout = new VerticalLayout();
			pedidoLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
		}
		return pedidoLayout;
	}
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setWidth(100,Sizeable.Unit.PERCENTAGE);
			centerPanel.setHeight(100,Sizeable.Unit.PERCENTAGE);
			if(getCartButton().isVisible()) centerPanel.setContent(getPanelLayout());
			else {
				cargarPedido();
				centerPanel.setContent(getPedidoLayout());
			}
		}
		return centerPanel;
	}
	
	//---Buttons------------------------------------------------------------------------------------------------------------------------------
	
	private IconButtonMobil getMenuButton() {
		if(menuButton == null) {
			menuButton = new IconButtonMobil(FontAwesome.NAVICON, fontSize, buttonSize);
			menuButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					//confirmarPedidoMobilView.setPedido(pedido);
					getUI().addWindow(new MenuMobilWindow(pedido, navigator, width, height,fontSize, buttonSize));
				}
			});
		}
		return menuButton;
	}
	
	private IconButtonMobil getCartButton() {
		if(cartButton == null) {
			cartButton = new IconButtonMobil(FontAwesome.SHOPPING_CART, fontSize, buttonSize);
			cartButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					cargarPedido();
					getCenterPanel().setContent(getPedidoLayout());
					cartButton.setVisible(false);
					listMenuButton.setVisible(true);
					getTotalLabel().setVisible(true);
				}
			});
		}
		return cartButton;
	}
	
	private IconButtonMobil getListMenuButton() {
		if(listMenuButton == null) {
			listMenuButton = new IconButtonMobil(FontAwesome.LIST, fontSize, buttonSize);
			listMenuButton.setVisible(false);
			listMenuButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					getCenterPanel().setContent(getPanelLayout());
					listMenuButton.setVisible(false);
					cartButton.setVisible(true);
					getTotalLabel().setVisible(false);
				}
			});
		}
		return listMenuButton;
	}
	
	//---Labels------------------------------------------------------------------------------------------------------------------------------
	
	private LabelMobil getMesaLabel() {
		if(mesaLabel == null) {
			mesaLabel = new LabelMobil("Mesa N°: "+(pedido != null?pedido.getNumeroMesa():""), fontSize);
		}
		return mesaLabel;
	}
	
	private LabelMobil getTotalLabel() {
		if(totalLabel == null) {
			totalLabel = new LabelMobil("Total: $"+getTotal(), fontSize);
			totalLabel.setVisible(false);
		}
		return totalLabel;
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	//----Logica-----------------------------------------------------------------------------------------------------------------------------
	
	private void cargarPedido() {
		getPedidoLayout().removeAllComponents();
		
		for(PedidoItem pedidoItem: pedido.getItems()) {
			getPedidoLayout().addComponent(new PedidoItemLayoutMobil(pedidoLayout, pedido, pedidoItem, width, height, fontSize,buttonSize));
		}
		
		getTotalLabel().update("Total: $"+getTotal(), fontSize);
	}
	
	private Double getTotal() {
		double total = 0;
		if(pedido != null)
		for(PedidoItem pedidoItem : pedido.getItems()) {
			total = total + pedidoItem.getCantidad() * pedidoItem.getMenuItem().getPrecio();
		}
		return total;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		for(Window window : getUI().getWindows()) {
    		window.close();
    	}
	}

}
