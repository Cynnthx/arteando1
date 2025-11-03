package com.example.arteando1.controladores;

import com.example.arteando1.dtos.ProgresoTestCrearDTO;
import com.example.arteando1.dtos.ProgresoTestDTO;
import com.example.arteando1.servicios.ProgresoTestServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progreso-test")
@AllArgsConstructor
public class ProgresoTestControlador {
    private final ProgresoTestServicio progresoTestServicio;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<ProgresoTestDTO> crear(@RequestBody ProgresoTestCrearDTO dto) {
        ProgresoTestDTO nuevo = progresoTestServicio.crearProgreso(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ProgresoTestDTO> actualizar(@PathVariable Integer id, @RequestBody ProgresoTestCrearDTO dto) {
        ProgresoTestDTO actualizado = progresoTestServicio.actualizarProgreso(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<ProgresoTestDTO>> obtenerTodos() {
        return ResponseEntity.ok(progresoTestServicio.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProgresoTestDTO> obtenerPorId(@PathVariable Integer id) {
        return progresoTestServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        progresoTestServicio.eliminarProgreso(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ProgresoTestDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(progresoTestServicio.obtenerPorUsuario(usuarioId));
    }

    // Obtener por test
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<ProgresoTestDTO>> obtenerPorTest(@PathVariable Integer testId) {
        return ResponseEntity.ok(progresoTestServicio.obtenerPorTest(testId));
    }
}
