package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findTopByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findFirstByNombreUsuario(String nombreUsuario);
}
