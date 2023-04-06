package com.mesero.manageBean;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mesero.bean.Menu;
import com.mesero.config.HibernateConfiguration;

public class ManageMenu {

	public void saveMenu(Menu menu){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.save(menu); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close();
		}
	}
	
	public void saveOrUpdateMenu(Menu menu){
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.saveOrUpdate(menu); 
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			if(session.isOpen())session.close(); 
		}
	}
	
	public List<Menu> listMenu() {
		Session session = HibernateConfiguration.getInstance().getSession();
		Transaction tx = null;
		
		List <Menu> listMenu = new ArrayList<Menu>();
		
		try{
			tx = session.beginTransaction();
			listMenu = (List<Menu>)session.createQuery("FROM Menu").list();
	        tx.commit();
	        session.close();
		}catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        if(session.isOpen()) session.close(); 
	    }
		return listMenu;
	}
	
	public void updateMenu(Menu menu){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
			session.update(menu); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	}
	   
	public void deleteMenu(String nombre){
		Session session = HibernateConfiguration.getInstance().getSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	Menu Menu = (Menu)session.get(Menu.class, nombre); 
	        session.delete(Menu); 
	        tx.commit();
	        session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	}
	
	public Menu getMenu(String nombre) {
		Session session = HibernateConfiguration.getInstance().getSession();
		Menu menu = null;
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	    	menu =  (Menu)session.get(Menu.class, nombre);
	    	tx.commit();
	    	session.close();
	    }catch (HibernateException e) {
	    	if (tx!=null) tx.rollback();
	    	e.printStackTrace(); 
	    }finally {
	    	if(session.isOpen())session.close();
	    }
	    return menu;
	}
}
