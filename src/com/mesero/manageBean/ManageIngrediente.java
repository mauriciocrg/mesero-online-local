package com.mesero.manageBean;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mesero.bean.Ingrediente;
import com.mesero.config.HibernateConfiguration;

public class ManageIngrediente {

	public void saveIngrediente(Ingrediente ingrediente){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.save(ingrediente); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close();
		}
	}
	
	public void saveOrUpdateIngrediente(Ingrediente ingrediente){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.saveOrUpdate(ingrediente); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close();
		}
	}
	
	public List listIngrediente() {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List listIngrediente = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			listIngrediente = session.createQuery("FROM Ingrediente").list();
	        tx.commit();
	        session.close();
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
		return listIngrediente;
	}
	
	public void updateIngrediente(Ingrediente ingrediente){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
			session.update(ingrediente); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	}
	   
	public void deleteIngrediente(int id){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	Ingrediente Ingrediente = (Ingrediente)session.get(Ingrediente.class, id); 
	        session.delete(Ingrediente); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	}
	
	public Ingrediente getIngrediente(int id) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Ingrediente ingrediente = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	ingrediente =  (Ingrediente)session.get(Ingrediente.class, id);
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	    return ingrediente;
	}
	
	public Ingrediente getIngrediente(String nombre) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Ingrediente ingrediente = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	
	    	Query query = session.createQuery("FROM Ingrediente I WHERE I.nombre = :nombre");
	    	query.setParameter("nombre",nombre);
	    	List results = query.list();
	    	ingrediente = results.size()>0?(Ingrediente)results.get(0):null;
	    	  
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	    return ingrediente;
	}
}
