package com.example.arteando1.servicios;

import com.example.arteando1.dtos.PuntajeUsuarioCrearDTO;
import com.example.arteando1.dtos.PuntajeUsuarioDTO;
import com.example.arteando1.modelos.PuntajeUsuario;
import com.example.arteando1.modelos.Test;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.PuntajeUsuarioRepositorio;
import com.example.arteando1.repositorios.TestRepositorio;
import com.example.arteando1.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PuntajeUsuarioServicio {
    private final PuntajeUsuarioRepositorio puntajeUsuarioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final TestRepositorio testRepositorio;

    // Crear nuevo puntaje
    public PuntajeUsuarioDTO crearPuntaje(PuntajeUsuarioCrearDTO dto) {
        Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        Test test = testRepositorio.findById(dto.getTestId())
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));

        PuntajeUsuario puntaje = new PuntajeUsuario();
        puntaje.setPuntaje(dto.getPuntaje());
        puntaje.setUsuario(usuario);
        puntaje.setTest(test);

        PuntajeUsuario guardado = puntajeUsuarioRepositorio.save(puntaje);
        return new PuntajeUsuarioDTO(guardado);
    }

    // Actualizar puntaje existente
    public PuntajeUsuarioDTO actualizarPuntaje(Integer id, PuntajeUsuarioCrearDTO dto) {
        PuntajeUsuario puntaje = puntajeUsuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Puntaje no encontrado con ID: " + id));

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
            puntaje.setUsuario(usuario);
        }

        if (dto.getTestId() != null) {
            Test test = testRepositorio.findById(dto.getTestId())
                    .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));
            puntaje.setTest(test);
        }

        puntaje.setPuntaje(dto.getPuntaje());
        PuntajeUsuario actualizado = puntajeUsuarioRepositorio.save(puntaje);
        return new PuntajeUsuarioDTO(actualizado);
    }

    // Obtener todos
    public List<PuntajeUsuarioDTO> obtenerTodos() {
        return puntajeUsuarioRepositorio.findAll().stream()
                .map(PuntajeUsuarioDTO::new)
                .toList();
    }

    // Obtener por ID
    public Optional<PuntajeUsuarioDTO> obtenerPorId(Integer id) {
        return puntajeUsuarioRepositorio.findById(id).map(PuntajeUsuarioDTO::new);
    }

    // Eliminar puntaje
    public void eliminarPuntaje(Integer id) {
        if (!puntajeUsuarioRepositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: puntaje no encontrado con ID " + id);
        }
        puntajeUsuarioRepositorio.deleteById(id);
    }

    // Obtener puntajes por usuario
    public List<PuntajeUsuarioDTO> obtenerPorUsuario(Integer usuarioId) {
        return puntajeUsuarioRepositorio.findAll().stream()
                .filter(p -> p.getUsuario() != null && p.getUsuario().getId().equals(usuarioId))
                .map(PuntajeUsuarioDTO::new)
                .toList();
    }

    // Obtener puntajes por test
    public List<PuntajeUsuarioDTO> obtenerPorTest(Integer testId) {
        return puntajeUsuarioRepositorio.findAll().stream()
                .filter(p -> p.getTest() != null && p.getTest().getId().equals(testId))
                .map(PuntajeUsuarioDTO::new)
                .toList();
    }
}
