package com.mesero.web.window;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.openqa.selenium.remote.server.handler.GetPageSource;

import com.mesero.bean.Pedido;
import com.mesero.web.pdf.TicketPDF;
import com.mesero.core.PrintServer;
import com.mesero.core.PrintServer.PrintDemon;
import com.mesero.manageBean.ManagePedido;
import com.mesero.web.pdf.TicketPDF;
import com.mesero.web.table.PedidoItemsTable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.akquinet.engineering.vaadin.html5.widgetset.NumberField;

public class PedidoDeliveryWindow extends Window implements WindowNotification {

	
private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE HH:mm",new Locale("ES"));
	
	private VerticalLayout mainLayout = null;
	private VerticalLayout contentCenterLayout = null;
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout headerLayout = null;
	private HorizontalLayout clientLayout = null;
	
	private HorizontalLayout rightButtonsLayout = null;
	
	private FormLayout client1Layout = null;
	private FormLayout client2Layout = null;
	private FormLayout totalLayout = null;
	
	private Label headerLabel = null;
	private TextField clienteField = null;
	private TextField numeroTicketField = null;
	private Label fechaLabel = null;
	
	private TextField direccionField = null;
	private TextField emailField = null;
	
	private NumberField descuentoField;
	
	private TextArea comentarioTextArea = null;
	
	private Panel centerPanel = null;
	private Panel pedidoItemsContentTablePanel = null;
	
	private PedidoItemsTable pedidoItemsTable = null;
	
	private Button cerrarButton = null;
	private Button okButton = null;
	private Button printButton = null;
	
	private CheckBox procesadoCheckBox = null;
	
	private Pedido pedido = null;
	
	private ManagePedido managePedido = new ManagePedido();
	
	public PedidoDeliveryWindow(Pedido pedido) {
		this.pedido = pedido;
		
		setClosable(false);
		setResizable(false);
		setModal(true);
		
		setWidth(780, Sizeable.Unit.PIXELS);
		setHeight(650, Sizeable.Unit.PIXELS);
		
		setContent(getMainLayout());
	}
	
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
			footerLayout.addComponents(getCerrarButton(),getRightButtonsLayout());
			footerLayout.setComponentAlignment(getCerrarButton(), Alignment.MIDDLE_LEFT);
			footerLayout.setComponentAlignment(getRightButtonsLayout(), Alignment.MIDDLE_RIGHT);
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
	
	private HorizontalLayout getRightButtonsLayout() {
		if(rightButtonsLayout == null) {
			rightButtonsLayout = new HorizontalLayout();
			rightButtonsLayout.setSpacing(true);
			rightButtonsLayout.setMargin(false);
			if(pedido.getEstado() == Pedido.ESTADO_PREPARADO) rightButtonsLayout.addComponent(getPrintButton());
			rightButtonsLayout.addComponent(getOkButton());
		}
		return rightButtonsLayout;
	}
	
	private FormLayout getClient1Layout() {
		if(client1Layout == null) {
			client1Layout = new FormLayout();
			client1Layout.setSpacing(true);
			client1Layout.setMargin(false);
			client1Layout.addComponents(getClienteField(),getNumeroTicketField());
		}
		return client1Layout;
	}	
	
	private FormLayout getClient2Layout() {
		if(client2Layout == null) {
			client2Layout = new FormLayout();
			client2Layout.setSpacing(true);
			client2Layout.setMargin(false);
			client2Layout.addComponents(getDireccionField(),getEmailField());
		}
		return client2Layout;
	}
	
	private FormLayout getTotalLayout() {
		if(totalLayout == null) {
			totalLayout = new FormLayout();
			totalLayout.setSpacing(true);
			totalLayout.setMargin(false);
			totalLayout.addComponents(getDescuentoField(),getProcesadoCheckBox());
		}
		return totalLayout;
	}
	
	private HorizontalLayout getClientLayout() {
		if(clientLayout == null) {
			clientLayout = new HorizontalLayout();
			clientLayout.setSpacing(true);
			clientLayout.setMargin(false);
			clientLayout.addComponents(getClient1Layout(),getClient2Layout());
		}
		return clientLayout;
	}	
	
	private VerticalLayout getContentCenterLayout() {
		if(contentCenterLayout == null) {
			contentCenterLayout = new VerticalLayout();
			contentCenterLayout.setSpacing(true);
			contentCenterLayout.setMargin(true);
			contentCenterLayout.addComponents(getClientLayout(),
					getFechaLabel(), 
					getComentarioTextArea(), 
					getPedidoItemsContentTablePanel(),
					getTotalLayout());
			//contentCenterLayout.setComponentAlignment(getIngredientesList(), Alignment.MIDDLE_CENTER);
			//contentCenterLayout.setComponentAlignment(getMenuBar(), Alignment.BOTTOM_CENTER);
		}
		return contentCenterLayout;
	}
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			centerPanel.setHeight(510,Sizeable.Unit.PIXELS);
			centerPanel.setContent(getContentCenterLayout());
		}
		return centerPanel;
	}
	
	private Panel getPedidoItemsContentTablePanel() {
		if(pedidoItemsContentTablePanel == null) {
			pedidoItemsContentTablePanel = new Panel();
			pedidoItemsContentTablePanel.setHeight(160, Sizeable.Unit.PIXELS);
			pedidoItemsContentTablePanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			pedidoItemsContentTablePanel.setContent(getPedidoItemsTable());
		}
		return pedidoItemsContentTablePanel;
	}
	
	//----TextField------------------------------------------------------------------------------------------------------
	
	private TextField getClienteField() {
		if(clienteField == null) {
			clienteField = new TextField();
			clienteField.setCaption("Cliente (Teléfono): ");
			clienteField.setValue(pedido.getCliente().getTelefono());
			clienteField.setReadOnly(true);
		}
		return clienteField;
	}
	
	private TextField getNumeroTicketField() {
		if(numeroTicketField == null) {
			numeroTicketField = new TextField();
			numeroTicketField.setCaption("N° de Ticket: ");
			numeroTicketField.setValue(""+pedido.getId_pedido());
			numeroTicketField.setReadOnly(true);
		}
		return numeroTicketField;
	}
	
	private TextField getDireccionField() {
		if(direccionField == null) {
			direccionField = new TextField();
			direccionField.setCaption("Dirección");
			direccionField.setValue(pedido.getCliente().getDireccion());
			direccionField.setReadOnly(true);
		}
		return direccionField;
	}
	
	private TextField getEmailField() {
		if(emailField == null) {
			emailField = new TextField();
			emailField.setCaption("E-Mail");
			emailField.setValue(pedido.getCliente().getEmail());
			emailField.setReadOnly(true);
		}
		return emailField;
	}
	
	private NumberField getDescuentoField() {
		if(descuentoField == null) {
			descuentoField = new NumberField("Descuento Total");
			descuentoField.setWidth(100, Sizeable.Unit.PERCENTAGE);
			descuentoField.setMax(100);
			descuentoField.setMin(0);
			descuentoField.setValue("0");
			descuentoField.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					getPedidoItemsTable().refresh(new Integer(descuentoField.getValue()));
				}
			});
		}
		return descuentoField;
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
	
	private Button getOkButton() {
		if(okButton == null) {
			okButton = new Button();
			okButton.setIcon(FontAwesome.CHECK);
			//cerrarButton.setWidth(150, Sizeable.Unit.PIXELS);
			okButton.addClickListener(new ClickListener() {
				 @Override
				 public void buttonClick(final ClickEvent event) {
					 if(procesadoCheckBox.getValue()) {
						 if(pedido.getEstado() == Pedido.ESTADO_PREPARADO) {
							 pedido.setEstado(Pedido.ESTADO_PROCESADO);
						 } else pedido.setEstado(Pedido.ESTADO_PREPARADO);
						 
						 
						 
						 managePedido.saveOrUpdatePedido(pedido);
					 }
					 close();
				 }
			});
		}
		return okButton;
	}
	
	private Button getPrintButton() {
		if(printButton == null) {
			printButton = new Button();
			printButton.setIcon(FontAwesome.PRINT);
			printButton.addClickListener(new ClickListener() {
				 @Override
				 public void buttonClick(final ClickEvent event) {
					 managePedido.saveOrUpdatePedido(pedido);
					 
					 TicketPDF ticket = new TicketPDF(pedido);
					 PrintDemon printDemon = PrintServer.getInstance().getPrintDemonLocal();
					 if(printDemon != null) {
						 printDemon.setWindowNotification(getThis());
						 printDemon.sendFile(ticket.getFile());
					 } else {
						 Notification.show("No se ha configurado una impresora local.", Notification.Type.TRAY_NOTIFICATION);
					 }
				 }
			});
		}
		return printButton;
	}
	
	private CheckBox getProcesadoCheckBox() {
		if(procesadoCheckBox == null) {
			procesadoCheckBox = new CheckBox(pedido.getEstado() == Pedido.ESTADO_NUEVO ? "Indicar como Preparado.":"Indicar como Procesado.");
		}
		return procesadoCheckBox;
	}
	
	//----Label----------------------------------------------------------------------------------------------------------
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label("Pedido (Delivery)");
			headerLabel.setStyleName("h2");
		}
		return headerLabel;
	}
	
	private Label getFechaLabel() {
		if(fechaLabel == null) {
			fechaLabel = new Label("Hora del Pedido: "+dateFormat.format(pedido.getFecha()));
		}
		return fechaLabel;
	}
	
	//----TextArea-------------------------------------------------------------------------------------------------------
	
	private TextArea getComentarioTextArea() {
		if(comentarioTextArea == null) {
			comentarioTextArea = new TextArea();
			comentarioTextArea.setCaption("Comentario:");
			comentarioTextArea.setWidth(100, Sizeable.Unit.PERCENTAGE);
			comentarioTextArea.setHeight(60, Sizeable.Unit.PIXELS);
			comentarioTextArea.setValue(pedido.getComentario());
			comentarioTextArea.setEnabled(false);
		}
		return comentarioTextArea;
	}
	
	//----Table----------------------------------------------------------------------------------------------------------
	
	private PedidoItemsTable getPedidoItemsTable() {
		if(pedidoItemsTable == null) {
			pedidoItemsTable = new PedidoItemsTable(pedido.getItems());
			/*pedidoItemsTable.addItemClickListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					// TODO Auto-generated method stub
					if(event.isDoubleClick()) {
						showMenuItem();
					}
				}
			});*/
		}
		return pedidoItemsTable;
	}

	private WindowNotification getThis() {
		return this;
	}
	
	@Override
	public void showMessage(String message) {
		new Notification(message, Notification.Type.TRAY_NOTIFICATION).show(getUI().getPage());
	}
}
