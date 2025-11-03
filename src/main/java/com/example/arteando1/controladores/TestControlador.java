package com.example.arteando1.controladores;

import com.example.arteando1.dtos.TestCrearDTO;
import com.example.arteando1.dtos.TestDTO;
import com.example.arteando1.modelos.Test;
import com.example.arteando1.servicios.TestServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/tests")
@AllArgsConstructor
public class TestControlador {
    private final TestServicio testServicio;

    //Crear un nuevo test
    @PostMapping("/crear")
    public ResponseEntity<TestDTO> crearTest(@RequestBody TestCrearDTO dto) {
        TestDTO nuevo = testServicio.crearTest(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //Listar todos los tests
    @GetMapping
    public ResponseEntity<List<TestDTO>> obtenerTodos() {
        return ResponseEntity.ok(testServicio.obtenerTodos());
    }

    // Buscar un test por ID
    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> obtenerPorId(@PathVariable Integer id) {
        return testServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Actualizar un test existente
    @PutMapping("/{id}")
    public ResponseEntity<TestDTO> actualizarTest(@PathVariable Integer id, @RequestBody TestCrearDTO dto) {
        TestDTO actualizado = testServicio.actualizarTest(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un test
    public ResponseEntity<Void> eliminarTest(@PathVariable Integer id) {
        testServicio.eliminarTest(id);
        return ResponseEntity.noContent().build();
    }
}
