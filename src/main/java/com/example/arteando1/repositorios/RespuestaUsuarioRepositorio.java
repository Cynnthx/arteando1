package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.RespuestaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaUsuarioRepositorio extends JpaRepository<RespuestaUsuario, Integer> {
}
