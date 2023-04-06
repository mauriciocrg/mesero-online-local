package com.mesero.web.window;

import java.text.SimpleDateFormat;
import java.util.Locale;


import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.manageBean.ManagePedido;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PedidoMeseroWindow  extends Window {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE HH:mm",new Locale("ES"));
	
	private VerticalLayout mainLayout = null;
	private VerticalLayout contentCenterLayout = null;
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout headerLayout = null;
	
	private Label headerLabel = null;
	private Label mesaLabel = null;
	private Label fechaLabel = null;
	
	private Panel centerPanel = null;
	
	private Button cerrarButton = null;
	
	private CheckBox procesadoCheckBox = null;
	
	private Pedido pedido = null;
	
	private ManagePedido managePedido = new ManagePedido();
	
	public PedidoMeseroWindow(Pedido pedido) {
		this.pedido = pedido;
		
		setWidth(400, Sizeable.Unit.PIXELS);
		setHeight(255, Sizeable.Unit.PIXELS);
		
		setClosable(false);
		setResizable(false);
		setModal(true);
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
			footerLayout.setStyleName("v-border");
			footerLayout.addComponent(getCerrarButton());
			footerLayout.setComponentAlignment(getCerrarButton(), Alignment.MIDDLE_RIGHT);
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
	
	private VerticalLayout getContentCenterLayout() {
		if(contentCenterLayout == null) {
			contentCenterLayout = new VerticalLayout();
			contentCenterLayout.setSpacing(true);
			contentCenterLayout.setMargin(true);
			contentCenterLayout.addComponents(getMesaLabel(), getFechaLabel(), getProcesadoCheckBox());
			//contentCenterLayout.setComponentAlignment(getIngredientesList(), Alignment.MIDDLE_CENTER);
			//contentCenterLayout.setComponentAlignment(getMenuBar(), Alignment.BOTTOM_CENTER);
		}
		return contentCenterLayout;
	}
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			centerPanel.setHeight(130,Sizeable.Unit.PIXELS);
			centerPanel.setContent(getContentCenterLayout());
		}
		return centerPanel;
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
					 managePedido.saveOrUpdatePedido(pedido);
					 close();
				 }
			});
		}
		return cerrarButton;
	}
	
	private CheckBox getProcesadoCheckBox() {
		if(procesadoCheckBox == null) {
			procesadoCheckBox = new CheckBox("Indicar como Procesado.");
			procesadoCheckBox.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					if(procesadoCheckBox.getValue()) pedido.setEstado(Pedido.ESTADO_PROCESADO);
					else pedido.setEstado(Pedido.ESTADO_NUEVO);
				}
			});
		}
		return procesadoCheckBox;
	}
	
	//----Label----------------------------------------------------------------------------------------------------------
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label("Pedido (LLama al Mesero)");
			headerLabel.setStyleName("h2");
		}
		return headerLabel;
	}
	
	private Label getMesaLabel() {
		if(mesaLabel == null) {
			mesaLabel = new Label("Mesa N°: "+pedido.getNumeroMesa());
		}
		return mesaLabel;
	}
	
	private Label getFechaLabel() {
		if(fechaLabel == null) {
			fechaLabel = new Label("Hora del Pedido: "+dateFormat.format(pedido.getFecha()));
		}
		return fechaLabel;
	}
}
