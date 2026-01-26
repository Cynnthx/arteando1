package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.PuntajeUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuntajeUsuarioRepositorio extends JpaRepository<PuntajeUsuario, Integer> {
    Optional<PuntajeUsuario> findByUsuarioIdAndTestId(Integer usuarioId, Integer testId);
}
