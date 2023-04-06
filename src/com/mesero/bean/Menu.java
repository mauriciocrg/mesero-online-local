package com.mesero.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Menu implements Serializable {

	private String nombre_menu = null;
	private Set<MenuItem> items = new HashSet<MenuItem>();

	public Menu() {}
	public Menu(String nombre_menu, 
				Set<MenuItem> items) {
		this.nombre_menu = nombre_menu;
		this.items = items;
	}
	
	public Set <MenuItem> getItems() {
		return items;
	}

	public void setItems(Set <MenuItem> items) {
		this.items = items;
	}
	
	public String getNombre_menu() {
		return this.nombre_menu;
	}
	public void setNombre_menu(String nombre_menu) {
		this.nombre_menu = nombre_menu;
	}
	
	public String toString() {
		return nombre_menu;
	}
}
