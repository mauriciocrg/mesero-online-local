package com.mesero.web.view;


import com.mesero.web.layout.ClientesVerticalLayout;
import com.mesero.web.layout.ConfigVerticalLayout;
import com.mesero.web.layout.FacturacionVerticalLayout;
import com.mesero.web.layout.MenuesVerticalLayout;
import com.mesero.web.layout.PedidosTabLayout;
import com.mesero.web.layout.PedidosVerticalLayout;
import com.mesero.web.layout.UsuarioVerticalLayout;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

public class MainView extends VerticalLayout implements View {

	private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
	
	private HorizontalLayout hLayout = new HorizontalLayout();
    private VerticalLayout mainContentLayout = new VerticalLayout();
    
    private PedidosVerticalLayout pedidosVerticalLayout = new PedidosVerticalLayout();
    private ConfigVerticalLayout configVerticalLayout = new ConfigVerticalLayout();
    private MenuesVerticalLayout menuesVerticalLayout = new MenuesVerticalLayout();
    private FacturacionVerticalLayout facturacionVerticalLayout = new FacturacionVerticalLayout();
    private ClientesVerticalLayout clientesVerticalLayout = new ClientesVerticalLayout();
    
    private Panel panelMain;
    private Label mainLabel;
		
	private Navigator navigator;
	
	public MainView(Navigator navigator) {
		
		this.navigator = navigator;
		
		setSizeFull();
        
        Button buttonPedidos = new Button("Pedidos");
        buttonPedidos.setIcon(FontAwesome.PASTE);
        buttonPedidos.setWidth(200, Sizeable.Unit.PIXELS);
        buttonPedidos.setPrimaryStyleName(ValoTheme.MENU_ITEM);     
        buttonPedidos.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				panelMain.setContent(pedidosVerticalLayout);
				panelMain.setCaption("<h2>Pedidos</h2>");
			}
        	
        });
        
        
        
        Button buttonMenues = new Button("Menues");
        buttonMenues.setIcon(FontAwesome.LIST);
        buttonMenues.setWidth(200, Sizeable.Unit.PIXELS);
        buttonMenues.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        buttonMenues.addClickListener(new ClickListener(){
        	@Override
			public void buttonClick(ClickEvent event) {
        		
        		panelMain.setContent(menuesVerticalLayout);
        		panelMain.setCaption("<h2>Menues</h2>");
			}
        });
        
        Button buttonFacturacion = new Button("Facturación");
        buttonFacturacion.setIcon(FontAwesome.DOLLAR);
        buttonFacturacion.setWidth(200, Sizeable.Unit.PIXELS);
        buttonFacturacion.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        buttonFacturacion.addClickListener(new ClickListener(){
        	@Override
			public void buttonClick(ClickEvent event) {
        		
        		panelMain.setContent(facturacionVerticalLayout);
        		panelMain.setCaption("<h2>Facturación</h2>");
			}
        });
        
        Button buttonClientes = new Button("Clientes");
        buttonClientes.setIcon(FontAwesome.USERS);
        buttonClientes.setWidth(200, Sizeable.Unit.PIXELS);
        buttonClientes.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        buttonClientes.addClickListener(new ClickListener(){
        	@Override
			public void buttonClick(ClickEvent event) {
        		
        		panelMain.setContent(clientesVerticalLayout);
        		panelMain.setCaption("<h2>Clientes</h2>");
			}
        });
        
        Button buttonConfig = new Button("Configuración");
        buttonConfig.setIcon(FontAwesome.COGS);
        buttonConfig.setWidth(200, Sizeable.Unit.PIXELS);
        buttonConfig.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        buttonConfig.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
        		panelMain.setContent(configVerticalLayout);
        		panelMain.setCaption("<h2>Configuración</h2>");
			}
        	
        });
        
        
        VerticalLayout menuContentLayout = new VerticalLayout();
        menuContentLayout.removeAllComponents();
        menuContentLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
        
        CssLayout menuLayout = new CssLayout();
        menuLayout.addStyleName(ValoTheme.MENU_PART);
        
        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem("Logout", FontAwesome.SIGN_OUT, new Command() {

            public void menuSelected(MenuItem selectedItem) {
                VaadinSession.getCurrent().getSession().invalidate();
                Page.getCurrent().reload();
            }
        });

        logoutMenu.addStyleName("user-menu");
        menuLayout.addComponent(logoutMenu);
        menuLayout.setStyleName("v-backgraund-gray");
        menuLayout.setHeight(100, Sizeable.Unit.PERCENTAGE);
        
        
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addComponent(buttonPedidos);
        menuItemsLayout.addComponent(buttonClientes);
        menuItemsLayout.addComponent(buttonMenues);
        menuItemsLayout.addComponent(buttonFacturacion);
        menuItemsLayout.addComponent(buttonConfig);
        
        menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
        menuLayout.addComponent(menuItemsLayout);
        /*
        VerticalLayout buttonsLayout = new VerticalLayout();
        buttonsLayout.removeAllComponents();
        buttonsLayout.addComponent(buttonPedidos);
        buttonsLayout.addComponent(buttonClientes);
        buttonsLayout.addComponent(buttonMenues);
        buttonsLayout.addComponent(buttonFacturacion);
        buttonsLayout.addComponent(buttonConfig);
        buttonsLayout.setStyleName("v-backgraund-gray");
        buttonsLayout.setSpacing(true);
        buttonsLayout.setMargin(true);
        
        menuContentLayout.setStyleName("v-backgraund-gray");
        menuContentLayout.addComponent(buttonsLayout);
        menuContentLayout.setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);
        */
        Panel menu = new Panel("<h2>Menu</h2>");
        menu.setStyleName("h2");
        
        menu.setHeight(100,Sizeable.Unit.PERCENTAGE);
        menu.setWidth(null);
        
        menu.setContent(menuLayout);
    
       
        mainContentLayout.setSpacing(true);
        mainContentLayout.setMargin(true);
        mainContentLayout.setMargin(new MarginInfo(true,true,true,true));
        
        mainContentLayout.setSizeFull();
        
        panelMain = new Panel();
        panelMain.setSizeFull();
        
        hLayout.removeAllComponents();
        hLayout.addComponent(menu);
        hLayout.addComponent(panelMain);
        hLayout.setExpandRatio(panelMain, 1.0f);
       
        hLayout.setSizeFull();
        
        removeAllComponents();
        addComponent(hLayout);
        setExpandRatio(hLayout, 1.0f);
        
        buttonPedidos.click();
        
        Thread thread = new Thread() {
    		public void run() {
    			while(true) {
    				
    				pedidosVerticalLayout.getPedidosTabLayout().refreshTable();
    				
    				try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
    		}
    	};
    	thread.start();
	}
	
	

	@Override
	public void enter(ViewChangeEvent event) {
		for(Window window : getUI().getWindows()) {
    		window.close();
    	}
	}
}
