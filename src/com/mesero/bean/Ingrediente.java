package com.mesero.bean;

import java.io.Serializable;

public class Ingrediente implements Serializable {

	private int id;
	private String nombre = null;

	public Ingrediente() {}
	public Ingrediente(String nombre) {
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return nombre;
	}
}
