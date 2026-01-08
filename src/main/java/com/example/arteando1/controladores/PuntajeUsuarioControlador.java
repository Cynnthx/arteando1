package com.example.arteando1.controladores;

import com.example.arteando1.dtos.PuntajeUsuarioCrearDTO;
import com.example.arteando1.dtos.PuntajeUsuarioDTO;
import com.example.arteando1.servicios.PuntajeUsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntajes-usuario")
@AllArgsConstructor
public class PuntajeUsuarioControlador {
    private final PuntajeUsuarioServicio puntajeUsuarioServicio;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<PuntajeUsuarioDTO> crear(@RequestBody PuntajeUsuarioCrearDTO dto) {
        PuntajeUsuarioDTO nuevo = puntajeUsuarioServicio.crearPuntaje(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<PuntajeUsuarioDTO> actualizar(@PathVariable Integer id, @RequestBody PuntajeUsuarioCrearDTO dto) {
        PuntajeUsuarioDTO actualizado = puntajeUsuarioServicio.actualizarPuntaje(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<PuntajeUsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(puntajeUsuarioServicio.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<PuntajeUsuarioDTO> obtenerPorId(@PathVariable Integer id) {
        return puntajeUsuarioServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        puntajeUsuarioServicio.eliminarPuntaje(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PuntajeUsuarioDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(puntajeUsuarioServicio.obtenerPorUsuario(usuarioId));
    }

    // Obtener por test
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<PuntajeUsuarioDTO>> obtenerPorTest(@PathVariable Integer testId) {
        return ResponseEntity.ok(puntajeUsuarioServicio.obtenerPorTest(testId));
    }
}
