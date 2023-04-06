package com.mesero.web.window;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.mesero.bean.Cliente;
import com.mesero.bean.Menu;
import com.mesero.bean.MenuItem;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.config.HibernateConfiguration;
import com.mesero.core.Status;
import com.mesero.web.layout.MenuItemLayout;
import com.mesero.web.layout.MenuLayout;
import com.mesero.web.layout.PedidoItemLayout;
import com.mesero.manageBean.ManageMenu;
import com.mesero.manageBean.ManagePedido;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CrearPedidoDeliveryWindow extends Window {

	private VerticalLayout mainLayout = null;
	private VerticalLayout menuLayout = null;
	private VerticalLayout pedidoLayout = null;
	private VerticalLayout contentCenterLayout = null;
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout headerLayout = null;
	private HorizontalLayout buttonsLayout = null;
	private FormLayout clientLayout = null;
	
	private Label headerLabel = null;
	private Label totalLabel = null;
	
	private TextField telefonoField = null;
	private TextField direccionField = null;
	private TextField emailField = null;
	
	private TextArea comentarioTextArea = null;
	
	private Panel centerPanel = null;
	private Panel pedidoItemsPanel = null;
	
	private Button cerrarButton = null;
	private Button okButton = null;
	private Button cartButton = null;
	private Button menuButton = null;
	
	private Cliente cliente;
	
	private Pedido pedido;
	
	private ManagePedido managePedido = new ManagePedido();
	private ManageMenu manageMenu = new ManageMenu();
	
	public CrearPedidoDeliveryWindow(Cliente cliente) {
		this.cliente = cliente;
		this.pedido = new Pedido();
		
		setClosable(false);
		setResizable(false);
		setModal(true);
		
		setWidth(700, Sizeable.Unit.PIXELS);
		setHeight(610, Sizeable.Unit.PIXELS);
		
		setContent(getMainLayout());
	}
	
	//-----Layout-----------------------------------------------------------------------------------------
	
	private VerticalLayout getMainLayout() {
		if(mainLayout == null) {
			mainLayout = new VerticalLayout();
			mainLayout.addComponent(getHeaderLayout());
			mainLayout.setComponentAlignment(getHeaderLayout(), Alignment.TOP_CENTER);
			mainLayout.addComponent(getCenterPanel());
			mainLayout.setComponentAlignment(getCenterPanel(), Alignment.MIDDLE_CENTER);
			mainLayout.addComponent(getFooterLayout());
			mainLayout.setComponentAlignment(getFooterLayout(), Alignment.BOTTOM_CENTER);
			mainLayout.setMargin(false);
		}
		return mainLayout;
	}
	
	private HorizontalLayout getFooterLayout() {
		if(footerLayout == null) {
			footerLayout = new HorizontalLayout();
			footerLayout.setMargin(false);
			footerLayout.setSpacing(true);
			footerLayout.setStyleName("v-border");
			footerLayout.addComponents(getCerrarButton(),getOkButton());
			footerLayout.setComponentAlignment(getCerrarButton(), Alignment.MIDDLE_LEFT);
			footerLayout.setComponentAlignment(getOkButton(), Alignment.MIDDLE_RIGHT);
			footerLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			footerLayout.setHeight(60,Sizeable.Unit.PIXELS);
		}
		return footerLayout;
	}
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.addComponent(getHeaderLabel());
		}
		return headerLayout;
	}
	
	private HorizontalLayout getButtonsLayout() {
		if(buttonsLayout == null) {
			buttonsLayout = new HorizontalLayout();
			buttonsLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			buttonsLayout.addComponents(getCartButton(),getMenuButton(),getTotalLabel());
			buttonsLayout.setComponentAlignment(getCartButton(), Alignment.MIDDLE_LEFT);
			buttonsLayout.setComponentAlignment(getMenuButton(), Alignment.MIDDLE_LEFT);
			buttonsLayout.setComponentAlignment(getTotalLabel(), Alignment.MIDDLE_RIGHT);
		}
		return buttonsLayout;
	}
	
	
	private FormLayout getClientLayout() {
		if(clientLayout == null) {
			clientLayout = new FormLayout();
			clientLayout.setSpacing(true);
			clientLayout.setMargin(false);
			clientLayout.addComponents(getTelefonoField(),getDireccionField(),getEmailField());
		}
		return clientLayout;
	}	
	
	private VerticalLayout getContentCenterLayout() {
		if(contentCenterLayout == null) {
			contentCenterLayout = new VerticalLayout();
			contentCenterLayout.setSpacing(true);
			contentCenterLayout.setMargin(true);
			contentCenterLayout.addComponents(getClientLayout(),
					getPedidoItemsPanel(),
					getButtonsLayout(), 
					getComentarioTextArea());
		}
		return contentCenterLayout;
	}
	
	private Panel getPedidoItemsPanel() {
		if(pedidoItemsPanel == null) {
			pedidoItemsPanel = new Panel();
			pedidoItemsPanel.setHeight(160, Sizeable.Unit.PIXELS);
			pedidoItemsPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			pedidoItemsPanel.setContent(getMenuLayout());
		}
		return pedidoItemsPanel;
	}
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			centerPanel.setHeight(480,Sizeable.Unit.PIXELS);
			centerPanel.setContent(getContentCenterLayout());
		}
		return centerPanel;
	}
	
	private VerticalLayout getMenuLayout() {
		if(menuLayout == null) {
			menuLayout = new VerticalLayout();
			menuLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			
			for(Menu menu : manageMenu.listMenu()) {
				menuLayout.addComponent(new MenuLayout(menu));
				
				for(MenuItem menuItem : menu.getItems()){
					if(menuItem.isHay() == 1) menuLayout.addComponent(new MenuItemLayout(pedido, menuItem));
				}
			}
		}
		return menuLayout;
	}
	
	private VerticalLayout getPedidoLayout() {
		if(pedidoLayout == null) {
			pedidoLayout = new VerticalLayout();
			pedidoLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
		}
		return pedidoLayout;
	}
	
	//----TextField------------------------------------------------------------------------------------------------------
	
	private TextField getTelefonoField() {
		if(telefonoField == null) {
			telefonoField = new TextField();
			telefonoField.setCaption("Cliente (Teléfono): ");
			telefonoField.setValue(cliente.getTelefono());
			telefonoField.setReadOnly(true);
		}
		return telefonoField;
	}
	
	private TextField getDireccionField() {
		if(direccionField == null) {
			direccionField = new TextField();
			direccionField.setCaption("Dirección");
			direccionField.setValue(cliente.getDireccion());
			direccionField.setReadOnly(true);
		}
		return direccionField;
	}
	
	private TextField getEmailField() {
		if(emailField == null) {
			emailField = new TextField();
			emailField.setCaption("E-Mail");
			emailField.setValue(cliente.getEmail());
			emailField.setReadOnly(true);
		}
		return emailField;
	}
	
	//----TextArea-------------------------------------------------------------------------------------------------------
	
	private TextArea getComentarioTextArea() {
		if(comentarioTextArea == null) {
			comentarioTextArea = new TextArea();
			comentarioTextArea.setCaption("Comentario:");
			comentarioTextArea.setWidth(100, Sizeable.Unit.PERCENTAGE);
			comentarioTextArea.setHeight(60, Sizeable.Unit.PIXELS);
			//comentarioTextArea.setValue(pedido.getComentario());
			//comentarioTextArea.setEnabled(false);
		}
		return comentarioTextArea;
	}
	
	//----Button---------------------------------------------------------------------------------------------------------
	
	private Button getCerrarButton() {
		if(cerrarButton == null) {
			cerrarButton = new Button();
			cerrarButton.setIcon(FontAwesome.REMOVE);
			//cerrarButton.setWidth(150, Sizeable.Unit.PIXELS);
			cerrarButton.addClickListener(new ClickListener() {
				 @Override
				 public void buttonClick(final ClickEvent event) {
					 close();
				 }
			});
		}
		return cerrarButton;
	}
	
	private Button getCartButton() {
		if(cartButton == null) {
			cartButton = new Button(FontAwesome.SHOPPING_CART);
			cartButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					cargarPedido();
					getPedidoItemsPanel().setContent(getPedidoLayout());
					getCartButton().setVisible(false);
					getMenuButton().setVisible(true);
				}
			});
		}
		return cartButton;
	}
	
	private Button getMenuButton() {
		if(menuButton == null) {
			menuButton = new Button(FontAwesome.LIST);
			menuButton.setVisible(false);
			menuButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					getPedidoItemsPanel().setContent(getMenuLayout());
					menuButton.setVisible(false);
					cartButton.setVisible(true);
				}
			});
		}
		return menuButton;
	}
	
	private Button getOkButton() {
		if(okButton == null) {
			okButton = new Button();
			okButton.setIcon(FontAwesome.CHECK);
			//cerrarButton.setWidth(150, Sizeable.Unit.PIXELS);
			okButton.addClickListener(new ClickListener() {
				 @Override
				 public void buttonClick(final ClickEvent event) {
					 
					 
					 pedido.setEstado(Pedido.ESTADO_NUEVO);
						pedido.setTipo_pedido(Pedido.TIPO_PEDIDO_DELIVERY);
						pedido.setFecha(new Date());
						pedido.setComentario(getComentarioTextArea().getValue());
						pedido.setCliente(cliente);
						
						Session session = HibernateConfiguration.getInstance().getSession();
						Transaction tx = null;
						
						try{
							
							session.save(pedido);
							
							tx = session.beginTransaction();
							for(PedidoItem pedidoItemx : pedido.getItems()) {
								pedidoItemx.setPedido(pedido);
								session.save(pedidoItemx);
							}
							 
							tx.commit();
							session.close();
							
						}catch (HibernateException e) {
							if (tx!=null) tx.rollback();
							e.printStackTrace(); 
						}finally {
							if(session.isOpen())session.close();
						}
						
						Status.getInstance().incrementarContador();
						
					 close();
				 }
			});
		}
		return okButton;
	}
	
	//----Label----------------------------------------------------------------------------------------------------------
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label("Nuevo Pedido (Delivery)");
			headerLabel.setStyleName("h2");
		}
		return headerLabel;
	}
	
	private Label getTotalLabel() {
		if(totalLabel == null) {
			totalLabel = new Label();
		}
		return totalLabel;
	}
	
	//----Logica-----------------------------------------------------------------------------------------------------------------------------
	
	private void cargarPedido() {
		getPedidoLayout().removeAllComponents();

		for(PedidoItem pedidoItem: pedido.getItems()) {
			getPedidoLayout().addComponent(new PedidoItemLayout(pedidoLayout, pedido, pedidoItem));
		}
		
		getTotalLabel().setValue("Total: $"+getTotal());
	}
	
	private Double getTotal() {
		double total = 0;
		if(pedido != null)
		for(PedidoItem pedidoItem : pedido.getItems()) {
			total = total + pedidoItem.getCantidad() * pedidoItem.getMenuItem().getPrecio();
		}
		return total;
	}
}
