package com.ey.usuario.servicio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ey.usuario.clases.Respuesta;
import com.ey.usuario.clases.RespuestaRegistro;
import com.ey.usuario.clases.RespuestaTelefono;
import com.ey.usuario.clases.TelefonoRegistro;
import com.ey.usuario.clases.UsuarioRegistro;
import com.ey.usuario.dao.UsuarioRepository;
import com.ey.usuario.entidades.Telefono;
import com.ey.usuario.entidades.Usuario;
import com.ey.usuario.servicio.IServicioUsuario;
import com.ey.usuario.variables.Codigos;

@Service
public class ServicioUsuarioImpl implements IServicioUsuario{

	@Autowired
	UsuarioRepository repository;
	
	@Override
	public RespuestaRegistro registrarUsuario(UsuarioRegistro usuario) {
		RespuestaRegistro _resRegistro = new RespuestaRegistro();
		try {
			boolean _correoValido = validarCorreo(usuario.getEmail());
			if(_correoValido) {
				boolean _passValida = validarContraseña(usuario.getPassword());
				if(_passValida) {
					
					Usuario _user = new Usuario();
					_user.setEmail(usuario.getEmail());
					_user.setName(usuario.getName());
					_user.setPassword(usuario.getPassword());
					List<Telefono> _fonos = new ArrayList<Telefono>();
					for (TelefonoRegistro _fonoReg : usuario.getPhones()) {
						Telefono _fono = new Telefono();
						_fono.setCitycode(_fonoReg.getCitycode());
						_fono.setContrycode(_fonoReg.getContrycode());
						_fono.setNumber(_fonoReg.getNumber());
						_fonos.add(_fono);
					}
					_user.setPhones(_fonos);
					_user.setIsactive(true);
					_user.setToken(java.util.UUID.randomUUID());
					
					_user = repository.save(_user);
					
					_resRegistro = formatRespuestaRegistro(_user);
					
				}else {
					Respuesta _respuesta = new Respuesta();
					_respuesta.setCodigo(Codigos.FORMATO_PASS.getCodigo()); 
					_respuesta.setMensaje(Codigos.FORMATO_PASS.getMensaje());
					_resRegistro.setRespuesta(_respuesta);
				}
			}else {
				Respuesta _respuesta = new Respuesta();
				_respuesta.setCodigo(Codigos.FORMATO_CORREO.getCodigo()); 
				_respuesta.setMensaje(Codigos.FORMATO_CORREO.getMensaje());
				_resRegistro.setRespuesta(_respuesta);
			}
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			e.printStackTrace();
			Respuesta _respuesta = new Respuesta();
			_respuesta.setCodigo(Codigos.CORREO_EXISTENTE.getCodigo()); 
			_respuesta.setMensaje(Codigos.CORREO_EXISTENTE.getMensaje());
			_resRegistro.setRespuesta(_respuesta);
		} catch (Exception e) {
			e.printStackTrace();
			Respuesta _respuesta = new Respuesta();
			_respuesta.setCodigo(Codigos.ERROR.getCodigo()); 
			_respuesta.setMensaje(Codigos.ERROR.getMensaje());
			_resRegistro.setRespuesta(_respuesta);
		}
		return _resRegistro;
	}
	
	private RespuestaRegistro formatRespuestaRegistro(Usuario usuario) {
		RespuestaRegistro _resRegistro = new RespuestaRegistro();
		try {
			_resRegistro.setIdUsuario(usuario.getId());
			_resRegistro.setName(usuario.getName());
			_resRegistro.setEmail(usuario.getEmail());
			_resRegistro.setPassword(usuario.getPassword());
			List<RespuestaTelefono> _fonosReg = new ArrayList<RespuestaTelefono>();
			for (Telefono _fonoReg : usuario.getPhones()) {
				RespuestaTelefono _fono = new RespuestaTelefono();
				_fono.setCitycode(_fonoReg.getCitycode());
				_fono.setContrycode(_fonoReg.getContrycode());
				_fono.setNumber(_fonoReg.getNumber());
				_fonosReg.add(_fono);
			}
			_resRegistro.setPhones(_fonosReg);
			_resRegistro.setCreated(usuario.getCreated());
			_resRegistro.setModified(usuario.getModified());
			_resRegistro.setLast_login(usuario.getLast_login());
			_resRegistro.setToken(usuario.getToken());
			_resRegistro.setIsactive(usuario.isIsactive());
			Respuesta _respuesta = new Respuesta();
			_respuesta.setCodigo(Codigos.EXITO.getCodigo());
			_respuesta.setMensaje(Codigos.EXITO.getMensaje());
			_resRegistro.setRespuesta(_respuesta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _resRegistro;
	}
	
	private boolean validarCorreo(String correo) {
		boolean _correoValido = false;
		
		String correo_regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern correo_pattern = Pattern.compile(correo_regex, Pattern.CASE_INSENSITIVE);
 
        if (correo_pattern.matcher(correo).matches())
        	_correoValido = true;
        
        return _correoValido;
	}
	
	private boolean validarContraseña(String pass) {
		boolean _passValida = false;
		
		// Patrón para validar la contraseña
		String password_regex = "^(?=(.*?\\d){2})(?=.*[A-Z])(?=(.*?[a-z]){2}).{4,}$";
		Pattern password_pattern = Pattern.compile(password_regex);
		
        if (password_pattern.matcher(pass).matches()) 
        	_passValida = true;
        
		return _passValida;
	}

}
