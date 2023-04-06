package com.mesero.manageBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

import com.mesero.bean.FacturacionDia;
import com.mesero.bean.FacturacionProducto;
import com.mesero.bean.Pedido;
import com.mesero.bean.PedidoItem;
import com.mesero.config.HibernateConfiguration;

public class ManagePedido {

	public void savePedido(Pedido pedido){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.save(pedido);
			
			for(PedidoItem pedidoItem : pedido.getItems()) session.save(pedidoItem);
			
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close(); 
		}
	}
	
	public void saveOrUpdatePedido(Pedido pedido){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			
			session.saveOrUpdate(pedido);
			
			for(PedidoItem pedidoItem : pedido.getItems()) session.saveOrUpdate(pedidoItem);
			
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close(); 
		}
	}
	
	public List <Pedido> listPedido(boolean ultimosDosDias) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List <Pedido>listPedido = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			
			
			DateTime dtHoy = new DateTime(new Date());
			Date hasta = dtHoy.plusDays(1).withTimeAtStartOfDay().toDate();
			
			DateTime dtAyer = new DateTime(new Date());
			Date desde = dtAyer.minusDays(1).withTimeAtStartOfDay().toDate();
			
			String searchFechasValue = "";
			
			if(ultimosDosDias) {
				searchFechasValue = searchFechasValue +
				" WHERE (P.fecha BETWEEN :desde AND :hasta) ";
			}
				
			Query query = session.createQuery("FROM Pedido P "+searchFechasValue+"ORDER BY P.fecha DESC");
			if(ultimosDosDias) {
				query.setParameter("desde", desde);
				query.setParameter("hasta", hasta);
			}
			
			listPedido = (List <Pedido>)query.list();
	        tx.commit();
	        session.close();
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
		return listPedido;
	}
	
	public List <Pedido> listPedidoReporte(boolean ultimosDosDias) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List <Pedido>listPedido = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			
			
			DateTime dtHoy = new DateTime(new Date());
			Date hasta = dtHoy.plusDays(1).withTimeAtStartOfDay().toDate();
			
			DateTime dtAyer = new DateTime(new Date());
			Date desde = dtAyer.minusDays(1).withTimeAtStartOfDay().toDate();
			
			String searchFechasValue = "";
			
			if(ultimosDosDias) {
				searchFechasValue = searchFechasValue +
				" AND (P.fecha BETWEEN :desde AND :hasta) ";
			}
				
			Query query = session.createQuery("FROM Pedido P WHERE P.tipo_pedido = "+Pedido.TIPO_PEDIDO_DELIVERY+" AND P.estado = "+Pedido.ESTADO_PROCESADO+searchFechasValue+" ORDER BY P.fecha DESC");
			if(ultimosDosDias) {
				query.setParameter("desde", desde);
				query.setParameter("hasta", hasta);
			}
			
			listPedido = (List <Pedido>)query.list();
	        tx.commit();
	        session.close();
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
		return listPedido;
	}
	
	public List<FacturacionDia> listFacturacionDiaria(Date desde, Date hasta) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List <FacturacionDia> listFacturacion = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			
			
			//;
			
			String query_1 = "SELECT DATE(P.fecha), SUM(PI.cantidad * PI.menuItem.precio - (PI.descuento * PI.cantidad * PI.menuItem.precio)/100) FROM Pedido P,PedidoItem PI WHERE PI.hay = 1 AND P.id_pedido = PI.pedido.id_pedido AND P.estado = "+Pedido.ESTADO_PROCESADO;
			String query_2 = " GROUP BY DATE(P.fecha) ORDER BY DATE(P.fecha) DESC";
			
			String searchFechasValue = "";
			
			int parameters = 0;
			
			if(desde != null && hasta != null) {
				searchFechasValue = searchFechasValue +
				" AND (P.fecha BETWEEN :desde AND :hasta) ";
				parameters = 1;
			} else
			if(desde != null && hasta == null) {
				searchFechasValue = searchFechasValue +  
				" AND P.fecha >= :desde ";
				parameters = 2;
			} else
			if(desde == null && hasta != null) {
				searchFechasValue = searchFechasValue + 
				" AND P.fecha <= :hasta ";
				parameters = 3;
			}
			 
			Query query = session.createQuery(query_1+searchFechasValue+query_2);
			
			switch (parameters) {
				case 1: 
					query.setParameter("desde", desde);
					query.setParameter("hasta", hasta);
				break;
				case 2: 
					query.setParameter("desde", desde);
				break;
				case 3: 
					query.setParameter("hasta", hasta);
				break;
			}
			
			List resultList = query.list();
	        tx.commit();
	        session.close();
	        
	        for (Object object : resultList) {
	        	
	            Object[] result = (Object[]) object;
	            
	            FacturacionDia facturacionDia = new FacturacionDia();
	            
	            facturacionDia.setFecha((Date) result[0]);
	            facturacionDia.setImporte((Double) result[1]);
	            
	            listFacturacion.add(facturacionDia);
	        }
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
		return listFacturacion;
	}
	
	public List<FacturacionProducto> listFacturacionPorProducto(Date desde, Date hasta) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List <FacturacionProducto> listFacturacion = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			
			
			//;
			
			String query_1 = "SELECT PI.menuItem.nombre_menuItem ,SUM(PI.cantidad * PI.menuItem.precio - ((PI.descuento * PI.cantidad * PI.menuItem.precio)/100)) FROM Pedido P,PedidoItem PI WHERE PI.hay = 1 AND P.id_pedido = PI.pedido.id_pedido AND P.estado = "+Pedido.ESTADO_PROCESADO;
			String query_2 = " GROUP BY PI.menuItem.nombre_menuItem ORDER BY SUM(PI.cantidad * PI.menuItem.precio) DESC";
			
			String searchFechasValue = "";
			String searchProductoValue = "";
			
			int parameters = 0;
			boolean producto = false;
			
			if(desde != null && hasta != null) {
				searchFechasValue = searchFechasValue +
				" AND (P.fecha BETWEEN :desde AND :hasta) ";
				parameters = 1;
			} else
			if(desde != null && hasta == null) {
				searchFechasValue = searchFechasValue +  
				" AND P.fecha >= :desde ";
				parameters = 2;
			} else
			if(desde == null && hasta != null) {
				searchFechasValue = searchFechasValue + 
				" AND P.fecha <= :hasta ";
				parameters = 3;
			}
			
			
			 
			Query query = session.createQuery(query_1+searchFechasValue+searchProductoValue+query_2);
			
			switch (parameters) {
				case 1: 
					query.setParameter("desde", desde);
					query.setParameter("hasta", hasta);
				break;
				case 2: 
					query.setParameter("desde", desde);
				break;
				case 3: 
					query.setParameter("hasta", hasta);
				break;
			}
			
			List resultList = query.list();
	        tx.commit();
	        session.close();
	        
	        for (Object object : resultList) {
	        	
	            Object[] result = (Object[]) object;
	            
	            FacturacionProducto facturacionProducto = new FacturacionProducto();
	            
	            facturacionProducto.setNombreMenuItem((String) result[0]);
	            facturacionProducto.setImporte((Double) result[1]);
	            
	            listFacturacion.add(facturacionProducto);
	        }
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
		return listFacturacion;
	}
	
	public List<FacturacionProducto> listFacturacionPorProducto(Date desde, Date hasta, String nombre_menuItem) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List <FacturacionProducto> listFacturacion = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			
			
			//;
			
			String query_1 = "SELECT P.fecha, PI.menuItem.nombre_menuItem ,PI.cantidad * PI.menuItem.precio - ((PI.descuento * PI.cantidad * PI.menuItem.precio)/100) FROM Pedido P,PedidoItem PI WHERE PI.hay = 1 AND P.id_pedido = PI.pedido.id_pedido AND P.estado = "+Pedido.ESTADO_PROCESADO;
			String query_2 = " ORDER BY DATE(P.fecha) DESC";
			
			String searchFechasValue = "";
			String searchProductoValue = "";
			
			int parameters = 0;
			boolean producto = false;
			
			if(nombre_menuItem != null && !nombre_menuItem.equals("")) { 
				searchProductoValue = searchProductoValue + 
				" AND PI.menuItem.nombre_menuItem = :producto_x ";
				producto = true;
			}
			
			if(desde != null && hasta != null) {
				searchFechasValue = searchFechasValue +
				" AND (P.fecha BETWEEN :desde AND :hasta) ";
				parameters = 1;
			} else
			if(desde != null && hasta == null) {
				searchFechasValue = searchFechasValue +  
				" AND P.fecha >= :desde ";
				parameters = 2;
			} else
			if(desde == null && hasta != null) {
				searchFechasValue = searchFechasValue + 
				" AND P.fecha <= :hasta ";
				parameters = 3;
			}
			
			
			 
			Query query = session.createQuery(query_1+searchFechasValue+searchProductoValue+query_2);
			
			switch (parameters) {
				case 1: 
					query.setParameter("desde", desde);
					query.setParameter("hasta", hasta);
				break;
				case 2: 
					query.setParameter("desde", desde);
				break;
				case 3: 
					query.setParameter("hasta", hasta);
				break;
			}
			
			if(producto) {
				query.setParameter("producto_x", nombre_menuItem);
			}
			
			List resultList = query.list();
	        tx.commit();
	        session.close();
	        
	        for (Object object : resultList) {
	        	
	            Object[] result = (Object[]) object;
	            
	            FacturacionProducto facturacionProducto = new FacturacionProducto();
	            
	            facturacionProducto.setFecha((Date) result[0]);
	            facturacionProducto.setNombreMenuItem((String) result[1]);
	            facturacionProducto.setImporte((Double) result[2]);
	            
	            listFacturacion.add(facturacionProducto);
	        }
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
		return listFacturacion;
	}
	
	public void updatePedido(Pedido pedido){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
			session.update(pedido); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	}
	  
	public void deletePedido(int id){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	Pedido pedido = (Pedido)session.get(Pedido.class, id);
	    	for(PedidoItem pedidoItem : pedido.getItems()) {
	    		session.delete(pedidoItem);
	    	}
	        session.delete(pedido); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	}
	
	public Pedido getUltimoPedidoEstadoPreparadoPorMesa(int numeroMesa) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Pedido pedido = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	
	    	Query query = session.createQuery("FROM Pedido P WHERE P.tipo_pedido = "+Pedido.TIPO_PEDIDO_LOCAL+" AND P.estado = "+Pedido.ESTADO_PREPARADO+" AND P.numeroMesa = :numeroMesa_x  ORDER BY DATE(P.fecha) DESC");
	    	query.setParameter("numeroMesa_x", numeroMesa);
	    	
	    	List <Pedido> listPedido = (List <Pedido>)query.list();
	    	
	    	if(listPedido.size() > 0) pedido = listPedido.get(0);
	    	
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	    return pedido;
	}
	
	public Pedido getPedido(int id) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Pedido pedido = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	pedido =  (Pedido)session.get(Pedido.class, id);
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	    return pedido;
	}
}
