package com.ey.usuario.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ey.usuario.clases.RespuestaRegistro;
import com.ey.usuario.clases.UsuarioRegistro;
import com.ey.usuario.servicio.IServicioUsuario;

@RestController
public class ControladorUsuario {
	
	@Autowired
	private IServicioUsuario servicioUsuario;
	
	@GetMapping
	public String health() {
		return "Servicio disponible";
	}
	
	@PostMapping("/registro")
	public RespuestaRegistro registrarUsuario(
			@RequestBody UsuarioRegistro usuario) {
		return servicioUsuario.c(usuario);
	}
	
}
