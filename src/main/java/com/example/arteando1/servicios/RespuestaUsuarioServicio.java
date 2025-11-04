package com.example.arteando1.servicios;

import com.example.arteando1.dtos.RespuestaUsuarioCrearDTO;
import com.example.arteando1.dtos.RespuestaUsuarioDTO;
import com.example.arteando1.modelos.*;
import com.example.arteando1.repositorios.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RespuestaUsuarioServicio {
    private final RespuestaUsuarioRepositorio respuestaUsuarioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PreguntaRepositorio preguntaRepositorio;
    private final OpcionRepositorio opcionRepositorio;

    // Crear nueva respuesta
    public RespuestaUsuarioDTO crearRespuesta(RespuestaUsuarioCrearDTO dto) {
        Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        Pregunta pregunta = preguntaRepositorio.findById(dto.getPreguntaId())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + dto.getPreguntaId()));

        Opcion opcion = opcionRepositorio.findById(dto.getOpcionId())
                .orElseThrow(() -> new RuntimeException("Opción no encontrada con ID: " + dto.getOpcionId()));

        RespuestaUsuario respuesta = new RespuestaUsuario();
        respuesta.setUsuario(usuario);
        respuesta.setPregunta(pregunta);
        respuesta.setOpcion(opcion);

        RespuestaUsuario guardada = respuestaUsuarioRepositorio.save(respuesta);
        return new RespuestaUsuarioDTO(guardada);
    }

    // Actualizar respuesta existente
    public RespuestaUsuarioDTO actualizarRespuesta(Integer id, RespuestaUsuarioCrearDTO dto) {
        RespuestaUsuario respuesta = respuestaUsuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + id));

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
            respuesta.setUsuario(usuario);
        }

        if (dto.getPreguntaId() != null) {
            Pregunta pregunta = preguntaRepositorio.findById(dto.getPreguntaId())
                    .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + dto.getPreguntaId()));
            respuesta.setPregunta(pregunta);
        }

        if (dto.getOpcionId() != null) {
            Opcion opcion = opcionRepositorio.findById(dto.getOpcionId())
                    .orElseThrow(() -> new RuntimeException("Opción no encontrada con ID: " + dto.getOpcionId()));
            respuesta.setOpcion(opcion);
        }

        RespuestaUsuario actualizada = respuestaUsuarioRepositorio.save(respuesta);
        return new RespuestaUsuarioDTO(actualizada);
    }

    // Obtener todas las respuestas
    public List<RespuestaUsuarioDTO> obtenerTodas() {
        return respuestaUsuarioRepositorio.findAll().stream()
                .map(RespuestaUsuarioDTO::new)
                .toList();
    }

    // Obtener respuesta por ID
    public Optional<RespuestaUsuarioDTO> obtenerPorId(Integer id) {
        return respuestaUsuarioRepositorio.findById(id).map(RespuestaUsuarioDTO::new);
    }

    // Eliminar respuesta
    public void eliminarRespuesta(Integer id) {
        if (!respuestaUsuarioRepositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: respuesta no encontrada con ID " + id);
        }
        respuestaUsuarioRepositorio.deleteById(id);
    }

    // Obtener respuestas por usuario
    public List<RespuestaUsuarioDTO> obtenerPorUsuario(Integer usuarioId) {
        return respuestaUsuarioRepositorio.findAll().stream()
                .filter(r -> r.getUsuario() != null && r.getUsuario().getId().equals(usuarioId))
                .map(RespuestaUsuarioDTO::new)
                .toList();
    }

    // Obtener respuestas por pregunta
    public List<RespuestaUsuarioDTO> obtenerPorPregunta(Integer preguntaId) {
        return respuestaUsuarioRepositorio.findAll().stream()
                .filter(r -> r.getPregunta() != null && r.getPregunta().getId().equals(preguntaId))
                .map(RespuestaUsuarioDTO::new)
                .toList();
    }
}
