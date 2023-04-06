package com.mesero.bean;

import java.io.Serializable;

public class Cliente implements Serializable {

	private String email;
	private String telefono;
	private String direccion;
	private String password;
	
	public Cliente() {}
	
	public Cliente(String email,
				   String telefono,
				   String direccion,
				   String password) {
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
