package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);

    Optional<Usuario> findTopByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findFirstByNombreUsuario(String nombreUsuario);
}
