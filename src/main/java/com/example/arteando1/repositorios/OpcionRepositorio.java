package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionRepositorio extends JpaRepository<Opcion, Integer> {
    List<Opcion> findByPreguntaId(Integer preguntaId);

    void deleteByPreguntaId(Integer preguntaId);
}
