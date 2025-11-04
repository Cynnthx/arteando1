package com.example.arteando1.controladores;

import com.example.arteando1.dtos.RespuestaUsuarioCrearDTO;
import com.example.arteando1.dtos.RespuestaUsuarioDTO;
import com.example.arteando1.servicios.RespuestaUsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestas-usuario")
@AllArgsConstructor
public class RespuestaUsuarioControlador {
    private final RespuestaUsuarioServicio respuestaUsuarioServicio;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<RespuestaUsuarioDTO> crear(@RequestBody RespuestaUsuarioCrearDTO dto) {
        RespuestaUsuarioDTO nueva = respuestaUsuarioServicio.crearRespuesta(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> actualizar(@PathVariable Integer id, @RequestBody RespuestaUsuarioCrearDTO dto) {
        RespuestaUsuarioDTO actualizada = respuestaUsuarioServicio.actualizarRespuesta(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<RespuestaUsuarioDTO>> obtenerTodas() {
        return ResponseEntity.ok(respuestaUsuarioServicio.obtenerTodas());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> obtenerPorId(@PathVariable Integer id) {
        return respuestaUsuarioServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        respuestaUsuarioServicio.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RespuestaUsuarioDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(respuestaUsuarioServicio.obtenerPorUsuario(usuarioId));
    }

    // Obtener por pregunta
    @GetMapping("/pregunta/{preguntaId}")
    public ResponseEntity<List<RespuestaUsuarioDTO>> obtenerPorPregunta(@PathVariable Integer preguntaId) {
        return ResponseEntity.ok(respuestaUsuarioServicio.obtenerPorPregunta(preguntaId));
    }
}
