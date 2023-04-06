package com.mesero.web.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.vaadin.server.VaadinService;

public class TicketPDF {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private Font boldFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
	private Font normalFont = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC);
	
	private File file;
	
	public TicketPDF(Pedido pedido) {
		try {
			
			file = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+File.separator+"tmp"+File.separator+"Ticket_"+new Date().getTime()+".pdf");
			
    		Document document = new Document();
    		document.setMargins(0, 0, 0, 0);
    		Rectangle rectangle = new Rectangle(300,2000);
    		document.setPageSize(rectangle);
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
	        document.add(createTable(pedido));
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
		PdfPCell cellProducto;
		cellProducto = new PdfPCell(new Phrase("Producto",boldFont));
		cellProducto.setBorder(Rectangle.BOTTOM);
		
		table.addCell(cellProducto);
		
		PdfPCell cellPrecio;
		cellPrecio = new PdfPCell(new Phrase("Precio",boldFont));
		cellPrecio.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		cellPrecio.setBorder(Rectangle.BOTTOM);
		
		table.addCell(cellPrecio);
	}
	
	
	private void addFooter(PdfPTable table, Double total) {
		PdfPCell cellProducto;
		cellProducto = new PdfPCell(new Phrase("TOTAL:",boldFont));
		cellProducto.setBorder(Rectangle.TOP);
		
		table.addCell(cellProducto);
		
		PdfPCell cellPrecio;
		cellPrecio = new PdfPCell(new Phrase(total.toString(),boldFont));
		cellPrecio.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		cellPrecio.setBorder(Rectangle.TOP);
		
		table.addCell(cellPrecio);
	}
	
	private PdfPTable createTable(Pedido pedido) {

		PdfPTable table = new PdfPTable(2);
		
		Double total = new Double(0);
		
		try {
			
			table.setWidthPercentage(90);
			table.setWidths(new float[]{ 70,30});
			
			PdfPCell cellTicket;
			cellTicket = new PdfPCell(new Phrase("No. Ticket: "+pedido.getId_pedido(),boldFont));
			cellTicket.setBorder(Rectangle.NO_BORDER);
			cellTicket.setColspan(2);
			
			table.addCell(cellTicket);
			
			PdfPCell cellFecha;
			cellFecha = new PdfPCell(new Phrase("Fecha: "+dateFormat.format(pedido.getFecha()),boldFont));
			cellFecha.setBorder(Rectangle.NO_BORDER);
			cellFecha.setColspan(2);
			
			table.addCell(cellFecha);
			
			PdfPCell cellSpan;
			cellSpan = new PdfPCell(new Phrase("",boldFont));
			cellSpan.setColspan(6);
			cellSpan.setBorder(Rectangle.NO_BORDER);
			
			table.addCell(cellSpan);
			
			
			addHeader(table);
			
			for(PedidoItem pedidoItem: pedido.getItems()) {
				
				if(pedidoItem.isHay() == 1) {
					String producto = pedidoItem.getMenuItem().getNombre_menuItem();
					Integer cantidad = pedidoItem.getCantidad();
					Integer descuento = pedidoItem.getDescuento();
					
					Double precio = pedidoItem.getMenuItem().getPrecio();
					
					String data = producto + " X " + cantidad.toString() + " (DTO " + descuento.toString() + "%)";  
					
					PdfPCell cellProducto;
					cellProducto = new PdfPCell(new Phrase(data,normalFont));
					cellProducto.setBorder(Rectangle.NO_BORDER);
					
					table.addCell(cellProducto);
					
					PdfPCell cellPrecio;
					cellPrecio = new PdfPCell(new Phrase(new Double((precio*cantidad) - ((precio*cantidad*descuento)/100)).toString(),normalFont));
					cellPrecio.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					cellPrecio.setBorder(Rectangle.NO_BORDER);
					
					table.addCell(cellPrecio);
					
					total = total + new Double((precio*cantidad) - ((precio*cantidad*descuento)/100));
				}
			}
			
			addFooter(table, total);
			
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
