package com.mesero.manageBean;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mesero.bean.MenuItem;
import com.mesero.config.HibernateConfiguration;

public class ManageMenuItem {

	public void saveMenuItem(MenuItem menuItem){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.save(menuItem); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close(); 
		}
	}
	
	public void saveOrUpdateMenuItem(MenuItem menuItem){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.saveOrUpdate(menuItem); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close(); 
		}
	}
	
	public List listMenuItem(String nombre_menu) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List listMenuItem = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			Query query= session.createQuery("FROM MenuItem M WHERE M.menu.nombre_menu = :nombre AND M.hay = : 1");
			query.setParameter("nombre", nombre_menu);
			listMenuItem = query.list();
	        tx.commit();
	        session.close();
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
		return listMenuItem;
	}
	
	public List listMenuItem() {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List listMenuItem = new ArrayList();
		
		try{
			tx = session.beginTransaction();
			Query query= session.createQuery("FROM MenuItem M ");
			listMenuItem = query.list();
	        tx.commit();
	        session.close();
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
		return listMenuItem;
	}
	
	public void updateMenuItem(MenuItem menuItem){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
			session.update(menuItem);
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	}
	   
	public void deleteMenuItem(String nombre){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	MenuItem MenuItem = (MenuItem)session.get(MenuItem.class, nombre); 
	        session.delete(MenuItem); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	}
	
	public MenuItem getMenuItem(String nombre) {
		Session session = HibernateConfiguration.getInstance().getSession();
		MenuItem menuItem = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	menuItem =  (MenuItem)session.get(MenuItem.class, nombre);
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	    return menuItem;
	}
	
	public MenuItem getMenuItem(String nombre_menuItem, String nombre_menu) {
		Session session = HibernateConfiguration.getInstance().getSession();
		MenuItem menuItem = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	Query query= session.createQuery("FROM MenuItem M WHERE M.nombre_menuItem = :nombre_menuItem_value AND M.menu.nombre_menu = :nombre_menu_value");
			query.setParameter("nombre_menu_value", nombre_menu);
			query.setParameter("nombre_menuItem_value", nombre_menuItem);
			
			System.out.println(query.getQueryString());
			
			List listMenuItem = query.list();
			menuItem = listMenuItem.size()>0?(MenuItem)listMenuItem.get(0):null;
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close(); 
	    }
	    return menuItem;
	}
}
