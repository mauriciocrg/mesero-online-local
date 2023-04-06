package com.mesero.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MenuItem implements Serializable {
	
	private String nombre_menuItem = null;
	private double precio = 0;
	private String imageName = null;
	private Menu menu;
	private Set<Ingrediente> ingredientes = new HashSet<Ingrediente>(0);
	private int hay; //1=true, 0=false

	public MenuItem() {}
	public MenuItem(String nombre_menuItem,
					double precio,
					String imageName,
					Menu menu,
					Set<Ingrediente> ingredientes) {
		this.nombre_menuItem = nombre_menuItem;
		this.precio = precio;
		this.imageName = imageName;
		this.menu = menu;
		this.ingredientes = ingredientes;
	}
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getNombre_menuItem() {
		return nombre_menuItem;
	}
	public void setNombre_menuItem(String nombre_menuItem) {
		this.nombre_menuItem = nombre_menuItem;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Menu getMenu() {
		return this.menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Set<Ingrediente> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(Set<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public String toString() {
		return nombre_menuItem;
	}
	public int isHay() {
		return hay;
	}
	public void setHay(int hay) {
		this.hay = hay;
	}
}
