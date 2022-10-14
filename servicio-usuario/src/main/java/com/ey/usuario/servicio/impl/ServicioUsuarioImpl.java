package com.ey.usuario.servicio.impl;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

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

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ServicioUsuarioImpl implements IServicioUsuario{

	private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
	
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
					
					Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					c.add(Calendar.MONTH, 12);
					long fechaExpira = c.getTimeInMillis();
					_user.setToken(crearJWT(usuario.getName(),usuario.getEmail(),fechaExpira));
					
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
		
		// Patrón para validar formato de correo
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
	
	public static String crearJWT( String nombre, String correo, long ttlMillis) {
		  
	    //El algoritmo de firma JWT que usaremos para firmar el token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	    long nowMillis = System.currentTimeMillis();
	    Date hoy = new Date(nowMillis);

	    //Firmaremos nuestro JWT con nuestro secreto ApiKey
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	    //Configuración de los Claims para JWT
	    JwtBuilder builder = Jwts.builder()
					    		.setId(UUID.randomUUID().toString())
					            .setIssuedAt(hoy)
					            .setSubject(nombre)
					            .setIssuer(correo)
					            .signWith(signatureAlgorithm, signingKey);
					  
	    //Si se ha especificado, agreguemos la caducidad
	    if (ttlMillis > 0) {
	        long expMillis = nowMillis + ttlMillis;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }  
	  
	    //Crea el JWT y lo serializa en una cadena compacta segura para URL
	    return builder.compact();
	}

}
