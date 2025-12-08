package com.example.arteando1.controladores;

import com.example.arteando1.dtos.PreguntaCrearDTO;
import com.example.arteando1.dtos.PreguntaDTO;
import com.example.arteando1.servicios.PreguntaServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preguntas")
@AllArgsConstructor
public class PreguntaControlador {
    private final PreguntaServicio preguntaServicio;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<PreguntaDTO> crearPregunta(@RequestBody PreguntaCrearDTO dto) {
        PreguntaDTO nueva = preguntaServicio.crearPregunta(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaDTO> actualizarPregunta(@PathVariable Integer id, @RequestBody PreguntaCrearDTO dto) {
        PreguntaDTO actualizada = preguntaServicio.actualizarPregunta(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<PreguntaDTO>> obtenerTodas() {
        return ResponseEntity.ok(preguntaServicio.obtenerTodas());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaDTO> obtenerPorId(@PathVariable Integer id) {
        return preguntaServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener preguntas por testId
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<PreguntaDTO>> obtenerPorTest(@PathVariable Integer testId) {
        List<PreguntaDTO> preguntas = preguntaServicio.obtenerPorTest(testId);
        return ResponseEntity.ok(preguntas);
    }


    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Integer id) {
        preguntaServicio.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }
}
