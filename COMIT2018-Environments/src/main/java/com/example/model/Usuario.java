package com.example.model;

public class Usuario {
	private int id;
	private String nombre;
	private String contrasenia;
	private boolean activo;
	
	public Usuario(int id, String nombre, String contrasenia, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.activo = activo;
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

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
	
	
}
