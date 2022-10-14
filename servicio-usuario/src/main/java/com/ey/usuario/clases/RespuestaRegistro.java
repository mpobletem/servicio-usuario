package com.ey.usuario.clases;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class RespuestaRegistro {

	private UUID idUsuario;
    private String name;
    private String email;
    private String password;
    private List<RespuestaTelefono> phones;
    private Calendar created;
    private Calendar modified;
    private Calendar last_login;
    private UUID token;
    private boolean isactive;
    private Respuesta respuesta;
    
    public RespuestaRegistro() {}
    
	public RespuestaRegistro(UUID idUsuario, String name, String email, String password, List<RespuestaTelefono> phones,
			Calendar created, Calendar modified, Calendar last_login, UUID token, boolean isactive, Respuesta respuesta) {
		super();
		this.idUsuario = idUsuario;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
		this.created = created;
		this.modified = modified;
		this.last_login = last_login;
		this.token = token;
		this.isactive = isactive;
		this.respuesta = respuesta;
	}
	
	public UUID getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UUID idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RespuestaTelefono> getPhones() {
		return phones;
	}

	public void setPhones(List<RespuestaTelefono> phones) {
		this.phones = phones;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getModified() {
		return modified;
	}

	public void setModified(Calendar modified) {
		this.modified = modified;
	}

	public Calendar getLast_login() {
		return last_login;
	}

	public void setLast_login(Calendar last_login) {
		this.last_login = last_login;
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Respuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "RespuestaRegistro [idUsuario=" + idUsuario + ", name=" + name + ", email=" + email + ", password="
				+ password + ", phones=" + (phones != null ? phones.subList(0, Math.min(phones.size(), maxLen)) : null)
				+ ", created=" + created + ", modified=" + modified + ", last_login=" + last_login + ", token=" + token
				+ ", isactive=" + isactive + ", mensaje=" + respuesta + "]";
	}
}
