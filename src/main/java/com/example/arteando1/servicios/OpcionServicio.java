package com.example.arteando1.servicios;

import com.example.arteando1.dtos.OpcionCrearDTO;
import com.example.arteando1.dtos.OpcionDTO;
import com.example.arteando1.modelos.Opcion;
import com.example.arteando1.modelos.Pregunta;
import com.example.arteando1.repositorios.OpcionRepositorio;
import com.example.arteando1.repositorios.PreguntaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OpcionServicio {
    private final OpcionRepositorio opcionRepositorio;
    private final PreguntaRepositorio preguntaRepositorio;

    // Crear nueva opción
    public OpcionDTO crearOpcion(OpcionCrearDTO dto) {
        Pregunta pregunta = preguntaRepositorio.findById(dto.getPreguntaId())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + dto.getPreguntaId()));

        Opcion opcion = new Opcion();
        opcion.setTexto(dto.getTexto());
        opcion.setEsCorrecta(dto.getEsCorrecta());
        opcion.setPregunta(pregunta);

        Opcion guardada = opcionRepositorio.save(opcion);
        return new OpcionDTO(guardada);
    }

    // Actualizar opción existente
    public OpcionDTO actualizarOpcion(Integer id, OpcionCrearDTO dto) {
        Opcion opcion = opcionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Opción no encontrada con ID: " + id));

        Pregunta pregunta = preguntaRepositorio.findById(dto.getPreguntaId())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + dto.getPreguntaId()));

        opcion.setTexto(dto.getTexto());
        opcion.setEsCorrecta(dto.getEsCorrecta());
        opcion.setPregunta(pregunta);

        Opcion actualizada = opcionRepositorio.save(opcion);
        return new OpcionDTO(actualizada);
    }

    // Obtener todas las opciones
    public List<OpcionDTO> obtenerTodas() {
        return opcionRepositorio.findAll().stream()
                .map(OpcionDTO::new)
                .toList();
    }

    // Obtener una opción por ID
    public Optional<OpcionDTO> obtenerPorId(Integer id) {
        return opcionRepositorio.findById(id).map(OpcionDTO::new);
    }

    // Eliminar una opción
    public void eliminarOpcion(Integer id) {
        if (!opcionRepositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Opción no encontrada con ID " + id);
        }
        opcionRepositorio.deleteById(id);
    }

    // Obtener todas las opciones de una pregunta específica
    public List<OpcionDTO> obtenerPorPregunta(Integer preguntaId) {
        return opcionRepositorio.findAll().stream()
                .filter(o -> o.getPregunta() != null && o.getPregunta().getId().equals(preguntaId))
                .map(OpcionDTO::new)
                .toList();
    }
}
