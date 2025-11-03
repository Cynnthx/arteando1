package com.example.arteando1.servicios;
import com.example.arteando1.dtos.ProgresoTestCrearDTO;
import com.example.arteando1.dtos.ProgresoTestDTO;
import com.example.arteando1.modelos.ProgresoTest;
import com.example.arteando1.modelos.Test;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.ProgresoTestRepositorio;
import com.example.arteando1.repositorios.TestRepositorio;
import com.example.arteando1.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProgresoTestServicio {
    private final ProgresoTestRepositorio progresoTestRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final TestRepositorio testRepositorio;

    // Crear nuevo progreso
    public ProgresoTestDTO crearProgreso(ProgresoTestCrearDTO dto) {
        Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        Test test = testRepositorio.findById(dto.getTestId())
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));

        ProgresoTest progreso = new ProgresoTest();
        progreso.setPorcentajeCompletado(dto.getPorcentajeCompletado());
        progreso.setUsuario(usuario);
        progreso.setTest(test);

        ProgresoTest guardado = progresoTestRepositorio.save(progreso);
        return new ProgresoTestDTO(guardado);
    }

    // Actualizar progreso existente
    public ProgresoTestDTO actualizarProgreso(Integer id, ProgresoTestCrearDTO dto) {
        ProgresoTest progreso = progresoTestRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Progreso no encontrado con ID: " + id));

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
            progreso.setUsuario(usuario);
        }

        if (dto.getTestId() != null) {
            Test test = testRepositorio.findById(dto.getTestId())
                    .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + dto.getTestId()));
            progreso.setTest(test);
        }

        progreso.setPorcentajeCompletado(dto.getPorcentajeCompletado());
        ProgresoTest actualizado = progresoTestRepositorio.save(progreso);
        return new ProgresoTestDTO(actualizado);
    }

    // Obtener todos los progresos
    public List<ProgresoTestDTO> obtenerTodos() {
        return progresoTestRepositorio.findAll().stream()
                .map(ProgresoTestDTO::new)
                .toList();
    }

    // Obtener progreso por ID
    public Optional<ProgresoTestDTO> obtenerPorId(Integer id) {
        return progresoTestRepositorio.findById(id).map(ProgresoTestDTO::new);
    }

    // Eliminar progreso
    public void eliminarProgreso(Integer id) {
        if (!progresoTestRepositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: progreso no encontrado con ID " + id);
        }
        progresoTestRepositorio.deleteById(id);
    }

    // Obtener progreso por usuario
    public List<ProgresoTestDTO> obtenerPorUsuario(Integer usuarioId) {
        return progresoTestRepositorio.findAll().stream()
                .filter(p -> p.getUsuario() != null && p.getUsuario().getId().equals(usuarioId))
                .map(ProgresoTestDTO::new)
                .toList();
    }

    // Obtener progreso por test
    public List<ProgresoTestDTO> obtenerPorTest(Integer testId) {
        return progresoTestRepositorio.findAll().stream()
                .filter(p -> p.getTest() != null && p.getTest().getId().equals(testId))
                .map(ProgresoTestDTO::new)
                .toList();
    }
}
