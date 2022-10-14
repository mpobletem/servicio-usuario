package com.ey.usuario.servicio;

import com.ey.usuario.clases.RespuestaRegistro;
import com.ey.usuario.clases.UsuarioRegistro;

public interface IServicioUsuario {
	
	public RespuestaRegistro registrarUsuario(UsuarioRegistro usuario);

}
