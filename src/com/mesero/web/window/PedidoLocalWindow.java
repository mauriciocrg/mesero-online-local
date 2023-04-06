package com.mesero.web.window;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.core.PrintServer;
import com.mesero.core.PrintServer.PrintDemon;
import com.mesero.manageBean.ManagePedido;
import com.mesero.web.pdf.TicketPDF;
import com.mesero.web.table.PedidoItemsTable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
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

public class PedidoLocalWindow extends Window implements WindowNotification {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE HH:mm",new Locale("ES"));
	
	private VerticalLayout mainLayout = null;
	private VerticalLayout contentCenterLayout = null;
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout headerLayout = null;
	private HorizontalLayout rightButtonsLayout = null;
	
	private FormLayout formLayout = null;
	private FormLayout totalLayout = null;
	
	private Label headerLabel = null;
	private Label fechaLabel = null;
	
	private TextField numeroTicketField = null;
	private TextField mesaField = null;
	
	private TextArea comentarioTextArea = null;
	
	private NumberField descuentoField;
	
	private Panel centerPanel = null;
	private Panel pedidoItemsContentTablePanel = null;
	
	private PedidoItemsTable pedidoItemsTable = null;
	
	private Button cerrarButton = null;
	private Button okButton = null;
	private Button printButton = null;
	
	private CheckBox procesadoCheckBox = null;
	
	private Pedido pedido = null;
	
	private ManagePedido managePedido = new ManagePedido();
	
	public PedidoLocalWindow(Pedido pedido) {
		this.pedido = pedido;
		
		setClosable(false);
		setResizable(false);
		setModal(true);
		
		setWidth(780, Sizeable.Unit.PIXELS);
		setHeight(640, Sizeable.Unit.PIXELS);
		
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
	
	private VerticalLayout getContentCenterLayout() {
		if(contentCenterLayout == null) {
			contentCenterLayout = new VerticalLayout();
			contentCenterLayout.setSpacing(true);
			contentCenterLayout.setMargin(true);
			contentCenterLayout.addComponents(getFormLayout(),
					getFechaLabel(), 
					getComentarioTextArea(), 
					getPedidoItemsContentTablePanel(), 
					getTotalLayout());
			//contentCenterLayout.setComponentAlignment(getIngredientesList(), Alignment.MIDDLE_CENTER);
			//contentCenterLayout.setComponentAlignment(getMenuBar(), Alignment.BOTTOM_CENTER);
		}
		return contentCenterLayout;
	}
	
	private FormLayout getFormLayout() {
		if(formLayout == null) {
			formLayout = new FormLayout();
			formLayout.setSpacing(true);
			formLayout.setMargin(false);
			formLayout.addComponents(getMesaField(),getNumeroTicketField());
		}
		return formLayout;
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
							 managePedido.saveOrUpdatePedido(pedido);
						 } else {
							 
							 if(pedido.getTipo_pedido() == pedido.TIPO_PEDIDO_LOCAL) {
								
								 Pedido pedidoInicial = managePedido.getUltimoPedidoEstadoPreparadoPorMesa(pedido.getNumeroMesa());
								 
								 if(pedidoInicial != null) {
									 
									 for(PedidoItem pedidoItem : pedido.getItems()) {
										 boolean tieneProducto = false;
										 for(PedidoItem pedidoItemInicial : pedidoInicial.getItems()) {
											 if(pedidoItemInicial.getMenuItem().getNombre_menuItem().equals(pedidoItem.getMenuItem().getNombre_menuItem())) {
												 pedidoItemInicial.setCantidad(pedidoItemInicial.getCantidad() + pedidoItem.getCantidad());
												 tieneProducto = true;
											 }
										 }
										 if(!tieneProducto) {
											 pedidoItem.setPedido(pedidoInicial);
											 pedidoInicial.getItems().add(pedidoItem);
										 }
									 }
									 
									 managePedido.saveOrUpdatePedido(pedidoInicial);
									 managePedido.deletePedido(pedido.getId_pedido());
									 
									 pedido = pedidoInicial;
								 } else {
									 pedido.setEstado(Pedido.ESTADO_PREPARADO);
									 managePedido.saveOrUpdatePedido(pedido);
								 }
								 
							 } else {
								 pedido.setEstado(Pedido.ESTADO_PREPARADO);
								 managePedido.saveOrUpdatePedido(pedido);
							 }
						 }
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
	
	//----TextField-------------------------------------------------------------------------------------------------------
	
	private TextField getNumeroTicketField() {
		if(numeroTicketField == null) {
			numeroTicketField = new TextField();
			numeroTicketField.setCaption("N° de Ticket: ");
			numeroTicketField.setValue(""+pedido.getId_pedido());
			numeroTicketField.setReadOnly(true);
		}
		return numeroTicketField;
	}
	
	private TextField getMesaField() {
		if(mesaField == null) {
			mesaField = new TextField("Mesa N°: ");
			mesaField.setValue(""+pedido.getNumeroMesa());
			mesaField.setReadOnly(true);
		}
		return mesaField;
	}
	
	//----Label----------------------------------------------------------------------------------------------------------
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label("Pedido (Normal)");
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
