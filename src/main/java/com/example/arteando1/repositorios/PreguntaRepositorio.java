package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepositorio extends JpaRepository<Pregunta, Integer> {
    List<Pregunta> findByTestId(Integer testId);


}
