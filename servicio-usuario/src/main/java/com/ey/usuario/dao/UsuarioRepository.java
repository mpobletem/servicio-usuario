package com.ey.usuario.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.usuario.entidades.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{

}
