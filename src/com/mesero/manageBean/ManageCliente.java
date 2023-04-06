package com.mesero.manageBean;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mesero.bean.Cliente;
import com.mesero.config.HibernateConfiguration;

public class ManageCliente {

	public void saveCliente(Cliente cliente){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.save(cliente); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close();
		}
	}
	
	public void saveOrUpdateCliente(Cliente cliente){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.saveOrUpdate(cliente); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close();
		}
	}
	
	public List <Cliente> listCliente() {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List <Cliente> listCliente = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			listCliente = (List <Cliente>)session.createQuery("FROM Cliente").list();
	        tx.commit();
	        session.close();
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
		return listCliente;
	}
	
	public void updateCliente(Cliente cliente){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
			session.update(cliente); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	}
	   
	public void deleteCliente(String telefono){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	Cliente Cliente = (Cliente)session.get(Cliente.class, telefono); 
	        session.delete(Cliente); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	}
	
	public Cliente getCliente(String telefono){
		Session session = HibernateConfiguration.getInstance().getSession();
		Cliente cliente = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	cliente =  (Cliente)session.get(Cliente.class, telefono);
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	    return cliente;
	}
}
