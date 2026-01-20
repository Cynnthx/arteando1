package com.example.arteando1.servicios;

import com.example.arteando1.dtos.OpcionDTO;
import com.example.arteando1.dtos.PreguntaConOpcionesDTO;
import com.example.arteando1.dtos.PreguntaCrearDTO;
import com.example.arteando1.dtos.PreguntaDTO;
import com.example.arteando1.modelos.Pregunta;
import com.example.arteando1.modelos.Test;
import com.example.arteando1.repositorios.OpcionRepositorio;
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
    private final OpcionRepositorio opcionRepositorio;

    // Crear una nueva pregunta
    public PreguntaDTO crearPregunta(PreguntaCrearDTO dto) {

        if (dto.getTexto() == null || dto.getTexto().isBlank()) {
            throw new IllegalArgumentException("El texto de la pregunta es obligatorio");
        }

        if (dto.getTestId() == null) {
            throw new IllegalArgumentException("La pregunta debe pertenecer a un test");
        }

        Test test = testRepositorio.findById(dto.getTestId())
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));

        Pregunta pregunta = new Pregunta();
        pregunta.setTexto(dto.getTexto());
        pregunta.setImagen(dto.getImagen());
        pregunta.setTest(test);

        return new PreguntaDTO(preguntaRepositorio.save(pregunta));
    }


    // Actualizar una pregunta existente
    public PreguntaDTO actualizarPregunta(Integer id, PreguntaCrearDTO dto) {

        if (dto.getTexto() == null || dto.getTexto().isBlank()) {
            throw new IllegalArgumentException("El texto de la pregunta es obligatorio");
        }

        Pregunta pregunta = preguntaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + id));

        Test test = testRepositorio.findById(dto.getTestId())
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));

        pregunta.setTexto(dto.getTexto());
        pregunta.setImagen(dto.getImagen());
        pregunta.setTest(test);

        return new PreguntaDTO(preguntaRepositorio.save(pregunta));
    }


    public void eliminarPregunta(Integer id) {
        if (!preguntaRepositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Pregunta no encontrada con ID " + id);
        }

        opcionRepositorio.deleteByPreguntaId(id);
        preguntaRepositorio.deleteById(id);
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


    // Obtener preguntas con opciones por testId
    public List<PreguntaConOpcionesDTO> obtenerPreguntasConOpcionesPorTest(Integer testId) {
        // 1. Buscamos todas las preguntas del test
        List<Pregunta> preguntas = preguntaRepositorio.findByTestId(testId);

        // 2. Convertimos cada Pregunta en un PreguntaConOpcionesDTO
        return preguntas.stream().map(p -> {
            // Buscamos las opciones de esta pregunta específica
            List<OpcionDTO> opciones = opcionRepositorio.findByPreguntaId(p.getId())
                    .stream()
                    .map(OpcionDTO::new) // Uso el constructor que tengo en OpcionDTO
                    .toList();

            return new PreguntaConOpcionesDTO(
                    p.getId(),
                    p.getTexto(),
                    p.getImagen(),
                    opciones
            );
        }).toList();
    }

    public List<PreguntaDTO> obtenerPorTest(Integer testId) {
        return preguntaRepositorio.findByTestId(testId).stream()
                .map(PreguntaDTO::new)
                .toList();
    }


}
