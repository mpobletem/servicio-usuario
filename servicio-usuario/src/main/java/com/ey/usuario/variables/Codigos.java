package com.ey.usuario.variables;

public enum Codigos {

	EXITO(1,"Se ha registrado al usuario con éxito"), 
	ERROR(0,"Se ha producido un error al registrar al usuario"),
	FORMATO_CORREO(2,"El formato del correo no es el correcto"),
	FORMATO_PASS(3,"La contrasena no cumple los campos mínimos"),
	CORREO_EXISTENTE(4,"El correo ya registrado");
	
	private int codigo;
	private String mensaje;
	
	private Codigos(int codigo, String mensaje){
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}
