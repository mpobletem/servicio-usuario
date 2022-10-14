package com.ey.usuario.entidades;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.WhereJoinTable;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "uuid")
	private UUID id;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;
    @OneToMany(cascade = {CascadeType.ALL})
    @WhereJoinTable(clause="DTYPE = 'telefono'")
    private List<Telefono> phones = null;
    @CreationTimestamp
    private Calendar created;
    private Calendar modified;
    @CreationTimestamp
    private Calendar last_login;
    private String token;
    private boolean isactive;

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

    public List<Telefono> getPhones() {
        return phones;
    }

    public void setPhones(List<Telefono> phones) {
        this.phones = phones;
    }

    public UUID getId() {
		return id;
	}

    public void setId(UUID id) {
		this.id = id;
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

    public String getToken() {
		return token;
	}

    public void setToken(String token) {
		this.token = token;
	}

    public boolean isIsactive() {
		return isactive;
	}

    public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

}
