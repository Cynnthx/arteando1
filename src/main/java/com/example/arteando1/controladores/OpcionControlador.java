package com.example.arteando1.controladores;

import com.example.arteando1.dtos.OpcionCrearDTO;
import com.example.arteando1.dtos.OpcionDTO;
import com.example.arteando1.servicios.OpcionServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opciones")
@AllArgsConstructor
public class OpcionControlador {
    private final OpcionServicio opcionServicio;

    // Crear opción
    @PostMapping("/crear")
    public ResponseEntity<OpcionDTO> crearOpcion(@RequestBody OpcionCrearDTO dto) {
        OpcionDTO nueva = opcionServicio.crearOpcion(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // Actualizar opción
    @PutMapping("/{id}")
    public ResponseEntity<OpcionDTO> actualizarOpcion(@PathVariable Integer id, @RequestBody OpcionCrearDTO dto) {
        OpcionDTO actualizada = opcionServicio.actualizarOpcion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<OpcionDTO>> obtenerTodas() {
        return ResponseEntity.ok(opcionServicio.obtenerTodas());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<OpcionDTO> obtenerPorId(@PathVariable Integer id) {
        return opcionServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOpcion(@PathVariable Integer id) {
        opcionServicio.eliminarOpcion(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todas las opciones de una pregunta específica
    @GetMapping("/pregunta/{preguntaId}")
    public ResponseEntity<List<OpcionDTO>> obtenerPorPregunta(@PathVariable Integer preguntaId) {
        return ResponseEntity.ok(opcionServicio.obtenerPorPregunta(preguntaId));
    }
}
