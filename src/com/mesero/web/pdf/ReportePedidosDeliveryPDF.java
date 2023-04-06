package com.mesero.web.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mesero.bean.Pedido;
import com.mesero.web.table.ReportePedidosTable;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CheckBox;

public class ReportePedidosDeliveryPDF {

	private Font boldFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
	private Font normalFont = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC);
	
	private File file;
	
	public ReportePedidosDeliveryPDF(ReportePedidosTable reportePedidosTable) {
		
		try {
			
			file = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"tmp"+File.separator+"Delivery_"+new Date().getTime()+".pdf");
			
    		Document document = new Document();
    		document.setMargins(0, 0, 0, 0);
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
	        document.add(createTable(reportePedidosTable));
	        document.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addHeader(PdfPTable table) {
		PdfPCell cellTicket;
		cellTicket = new PdfPCell(new Phrase("Ticket",boldFont));
		cellTicket.setBorder(Rectangle.BOTTOM);
		
		table.addCell(cellTicket);
		
		PdfPCell cellNombre;
		cellNombre = new PdfPCell(new Phrase("Nómbre",boldFont));
		cellNombre.setBorder(Rectangle.BOTTOM);
		
		table.addCell(cellNombre);
	
		PdfPCell cellDireccion;
		cellDireccion = new PdfPCell(new Phrase("Dirección",boldFont));
		cellDireccion.setBorder(Rectangle.BOTTOM);
		
		table.addCell(cellDireccion);
		
		PdfPCell cellPrecio;
		cellPrecio = new PdfPCell(new Phrase("Costo",boldFont));
		cellPrecio.setBorder(Rectangle.BOTTOM);
		
		table.addCell(cellPrecio);
	}
	
	private PdfPTable createTable(ReportePedidosTable peportePedidosTable) {

		PdfPTable table = new PdfPTable(4);
		
		try {
			
			table.setWidthPercentage(90);
			table.setWidths(new float[]{ 15, 25, 35, 25});
			
			addHeader(table);
			
			for(Pedido pedido: (Collection<Pedido>)peportePedidosTable.getContainerDataSource().getItemIds()) {
				Item item = peportePedidosTable.getItem(pedido);
				Integer ticket = (Integer)item.getItemProperty(ReportePedidosTable.COLUMN_TICKET).getValue();
				String telefono = (String)item.getItemProperty(ReportePedidosTable.COLUMN_NOMBRE).getValue();
				String direccion = (String)item.getItemProperty(ReportePedidosTable.COLUMN_DIRECCION).getValue();
				Double precio = (Double)item.getItemProperty(ReportePedidosTable.COLUMN_PRECIO).getValue();
				Boolean reportar = ((CheckBox)(item.getItemProperty(ReportePedidosTable.COLUMN_REPORTAR).getValue())).getValue();
				
				if(reportar) {
					PdfPCell cellTicket;
					cellTicket = new PdfPCell(new Phrase(ticket.toString(),normalFont));
					cellTicket.setBorder(Rectangle.NO_BORDER);
					
					table.addCell(cellTicket);
					
					PdfPCell cellNombre;
					cellNombre = new PdfPCell(new Phrase(telefono,normalFont));
					cellNombre.setBorder(Rectangle.NO_BORDER);
					
					table.addCell(cellNombre);
				
					PdfPCell cellDireccion;
					cellDireccion = new PdfPCell(new Phrase(direccion,normalFont));
					cellDireccion.setBorder(Rectangle.NO_BORDER);
					
					table.addCell(cellDireccion);
					
					PdfPCell cellPrecio;
					cellPrecio = new PdfPCell(new Phrase(precio.toString(),normalFont));
					cellPrecio.setBorder(Rectangle.NO_BORDER);
					
					table.addCell(cellPrecio);
				}
				//System.out.println(telefono+" - "+direccion+" - "+reportar);
			}
			//System.out.println(" - -------------- - ");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}
	
	public File getFile() {
		return this.file;
	}
}
