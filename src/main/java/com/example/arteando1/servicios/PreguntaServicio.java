package com.example.arteando1.servicios;

import com.example.arteando1.dtos.PreguntaCrearDTO;
import com.example.arteando1.dtos.PreguntaDTO;
import com.example.arteando1.modelos.Pregunta;
import com.example.arteando1.modelos.Test;
import com.example.arteando1.repositorios.PreguntaRepositorio;
import com.example.arteando1.repositorios.TestRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PreguntaServicio {
    private final PreguntaRepositorio preguntaRepositorio;
    private final TestRepositorio testRepositorio;

    // Crear una nueva pregunta
    public PreguntaDTO crearPregunta(PreguntaCrearDTO dto) {
        Test test = testRepositorio.findById(dto.getTestId())
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));

        Pregunta pregunta = new Pregunta();
        pregunta.setTexto(dto.getTexto());
        pregunta.setImagen(dto.getImagen());
        pregunta.setTest(test);

        Pregunta guardada = preguntaRepositorio.save(pregunta);
        return new PreguntaDTO(guardada);
    }

    // Actualizar una pregunta existente
    public PreguntaDTO actualizarPregunta(Integer id, PreguntaCrearDTO dto) {
        Pregunta pregunta = preguntaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + id));

        Test test = testRepositorio.findById(dto.getTestId())
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));

        pregunta.setTexto(dto.getTexto());
        pregunta.setImagen(dto.getImagen());
        pregunta.setTest(test);

        Pregunta actualizada = preguntaRepositorio.save(pregunta);
        return new PreguntaDTO(actualizada);
    }

    // Obtener todas las preguntas
    public List<PreguntaDTO> obtenerTodas() {
        return preguntaRepositorio.findAll().stream()
                .map(PreguntaDTO::new)
                .toList();
    }

    // Obtener una pregunta por ID
    public Optional<PreguntaDTO> obtenerPorId(Integer id) {
        return preguntaRepositorio.findById(id).map(PreguntaDTO::new);
    }

    // Eliminar una pregunta
    public void eliminarPregunta(Integer id) {
        if (!preguntaRepositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Pregunta no encontrada con ID " + id);
        }
        preguntaRepositorio.deleteById(id);
    }
}
