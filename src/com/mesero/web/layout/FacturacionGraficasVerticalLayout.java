package com.mesero.web.layout;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.beust.jcommander.internal.Lists;
import com.mesero.bean.FacturacionDia;
import com.mesero.bean.FacturacionProducto;
import com.mesero.bean.MenuItem;
import com.mesero.manageBean.ManagePedido;
//import com.thoughtworks.selenium.webdriven.commands.GetValue;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class FacturacionGraficasVerticalLayout extends VerticalLayout {
	
	private HorizontalLayout fechasLayout;
	private FormLayout formLayout;
	private Panel chartPanel;
	
	private DateField fechaDesdeDateField;
	private DateField fechaHastaDateField;
	
	private Label label;
	
	private Button facturacionPorDiaButton;
	private Button productoMasVendidoButton;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private ManagePedido managePedido = new ManagePedido();
	
	public FacturacionGraficasVerticalLayout() {
		setCaption("Graficas");
		setIcon(FontAwesome.AREA_CHART);
		removeAllComponents();
		setSpacing(true);
		setMargin(true);
		setWidth(100, Sizeable.Unit.PERCENTAGE);
		setHeight(100, Sizeable.Unit.PERCENTAGE);
        
		
        addComponents(getFormLayout(),getChartPanel());
        setExpandRatio(getChartPanel(), 1.0f);
	}
	
	//----- LAYOUT ---------------------------------------------------------------------------------------------------------
	
	
	private FormLayout getFormLayout() {
		if(formLayout == null) {
			formLayout = new FormLayout();
			formLayout.setMargin(false);
			formLayout.setSpacing(true);
			formLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
			formLayout.addComponents(getLabel(),getFechasLayout());
		}
		return formLayout;
	}
	
	private HorizontalLayout getFechasLayout() {
		if(fechasLayout == null) {
			fechasLayout = new HorizontalLayout();
			fechasLayout.setSpacing(true);
			fechasLayout.addComponents(getFechaDesdeDateField(),getFechaHastaDateField(),getFacturacionPorDiaButton(),getProductoMasVendidoButton());
			fechasLayout.setCaption("Lapso de Fechas:");
		}
		return fechasLayout;
	}
	
	private Panel getChartPanel() {
		if(chartPanel == null) {
			chartPanel = new Panel("Grafica");
			chartPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			chartPanel.setHeight(100, Sizeable.Unit.PERCENTAGE);
		}
		return chartPanel;
	}
	
	//----- FIELDS ---------------------------------------------------------------------------------------------------------
	
	private DateField getFechaDesdeDateField() {
		if(fechaDesdeDateField == null) {
			fechaDesdeDateField = new DateField();
		}
		return fechaDesdeDateField;
	}
	
	private DateField getFechaHastaDateField() {
		if(fechaHastaDateField == null) {
			fechaHastaDateField = new DateField();
		}
		return fechaHastaDateField;
	}
	
	private Label getLabel() {
		if(label == null) {
			label = new Label("Graficas de Facturación");
			label.setStyleName("h2");
		}
		return label;
	}
	
	//----- BUTONS ---------------------------------------------------------------------------------------------------------
	
	private Button getFacturacionPorDiaButton() {
		if(facturacionPorDiaButton == null) {
			facturacionPorDiaButton = new Button("Facturación por día");
			facturacionPorDiaButton.setIcon(FontAwesome.BAR_CHART);
			facturacionPorDiaButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					
					Embedded chart = new Embedded();
			        chart.setWidth(100, Sizeable.Unit.PERCENTAGE);
			        chart.setHeight(100, Sizeable.Unit.PERCENTAGE);
			        chart.setType(Embedded.TYPE_BROWSER);
			        
			        StreamResource res = new StreamResource(getFacturacionDiariaChartStream(), "1");
			        res.setMIMEType("text/html; charset=utf-8");
			        
			        chart.setSource(res);
			        
			        getChartPanel().setContent(chart);
					
				}});
		}
		return facturacionPorDiaButton;
	}
	
	private Button getProductoMasVendidoButton() {
		if(productoMasVendidoButton == null) {
			productoMasVendidoButton = new Button("Producto mas vendido");
			productoMasVendidoButton.setIcon(FontAwesome.PIE_CHART);
			productoMasVendidoButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					
					Embedded chart = new Embedded();
			        chart.setWidth(100, Sizeable.Unit.PERCENTAGE);
			        chart.setHeight(100, Sizeable.Unit.PERCENTAGE);
			        chart.setType(Embedded.TYPE_BROWSER);
			        
			        StreamResource res = new StreamResource(getFacturacionPorProductoChartStream(), "1");
			        res.setMIMEType("text/html; charset=utf-8");
			        
			        chart.setSource(res);
			        
			        getChartPanel().setContent(chart);
					
				}});
		}
		return productoMasVendidoButton;
	}
	
	//----- LOGICA ---------------------------------------------------------------------------------------------------------
	
	private ChartStreamSource getFacturacionPorProductoChartStream() { 
		String data = "";
		
		for(FacturacionProducto facturacionProducto: managePedido.listFacturacionPorProducto(getFechaDesdeDateField().getValue(), getFechaHastaDateField().getValue())) {
			data = data + "['"+facturacionProducto.getNombreMenuItem()+"',"+facturacionProducto.getImporte()+"],";
		}
		
		data = data.substring(0, data.length() -1);
		
		String HTML = "<html>"
				  + "<head>"
				  + "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
				  + "<script type=\"text/javascript\">"
				  + "	google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
				  + "	google.setOnLoadCallback(drawChart);"
				  + "	"
				  + "	function drawChart() {"
				  + "		var data = google.visualization.arrayToDataTable([['Producto','Importe'],"+data+"]);"
				  + "		var options = {"
				  + "					title: 'Facturación por Producto (Fecha de generado:"+dateTimeFormat.format(new Date())+")'"
				  + "		};"
				  + "		var chart = new google.visualization.PieChart(document.getElementById('chart_div'));"
				  + ""
				  + "	chart.draw(data, options);}"
				  + "</script>"
				  + "</head>"
				  + "<body>"
				  + "	<div id=\"chart_div\" style=\"width: 100%; height: 100%;\">"
				  + "	</div>"
				  + "</body>"
				  + "</html>";
//900px; height: 500px
	//System.out.println(HTML);
	
	return new ChartStreamSource(HTML);
	}
	
	
	private ChartStreamSource getFacturacionDiariaChartStream() {
		
		String data = "";
		
		List <FacturacionDia> listFacturacionDia = managePedido.listFacturacionDiaria(getFechaDesdeDateField().getValue(), getFechaHastaDateField().getValue());
		
		Collections.reverse(listFacturacionDia);
		
		for(FacturacionDia facDia: listFacturacionDia) {
			data = data + "['"+dateFormat.format(facDia.getFecha())+"',"+facDia.getImporte()+"],";
		}
		
		data = data.substring(0, data.length() -1);
		
		//System.out.println(data);
		
		String HTML = "<html>"
					  + "<head>"
					  + "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
					  + "<script type=\"text/javascript\">"
					  + "	google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
					  + "	google.setOnLoadCallback(drawChart);"
					  + "	"
					  + "	function drawChart() {"
					  + "		var data = google.visualization.arrayToDataTable([['Fecha','Importe'],"+data+"]);"
					  + "		var options = {"
					  + "					title: 'Facturación por Día (Fecha de generado:"+dateTimeFormat.format(new Date())+")',"
					  + "					vAxis: {title: 'Importe'},"
					  + "					hAxis: {title: 'Fecha'},"
					  + "					seriesType: 'bars',"
					  + "					orientation: 'horizontal'"
					  + "		};"
					  + "		var chart = new google.visualization.BarChart(document.getElementById('chart_div'));"
					  + ""
					  + "	chart.draw(data, options);}"
					  + "</script>"
					  + "</head>"
					  + "<body>"
					  + "	<div id=\"chart_div\" style=\"width: 100%; height: 100%;\">"
					  + "	</div>"
					  + "</body>"
					  + "</html>";

		//System.out.println(HTML);
		
		return new ChartStreamSource(HTML);
	}
	
	
	private static class ChartStreamSource implements StreamSource {
        
        private String HTML;
        public ChartStreamSource(String HTML) {
        	this.HTML = HTML;
        }
        
        public InputStream getStream() {
            return new ByteArrayInputStream(HTML.getBytes());
        }
        
    }
}
