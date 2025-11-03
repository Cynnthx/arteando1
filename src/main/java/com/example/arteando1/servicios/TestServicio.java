package com.example.arteando1.servicios;


import com.example.arteando1.dtos.TestCrearDTO;
import com.example.arteando1.dtos.TestDTO;
import com.example.arteando1.modelos.Categoria;
import com.example.arteando1.modelos.Test;
import com.example.arteando1.repositorios.CategoriaRepositorio;
import com.example.arteando1.repositorios.TestRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TestServicio {

    private final TestRepositorio testRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;

    // Crear un nuevo test
    public TestDTO crearTest(TestCrearDTO dto) {
        Categoria categoria = categoriaRepositorio.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        Test test = new Test();
        test.setTitulo(dto.getTitulo());
        test.setDescripcion(dto.getDescripcion());
        test.setDificultad(dto.getDificultad());
        test.setCategoria(categoria);

        Test guardado = testRepositorio.save(test);
        return new TestDTO(guardado);
    }


    //Obtener todos los tests
    public List<TestDTO> obtenerTodos() {
        return testRepositorio.findAll().stream()
                .map(TestDTO::new)
                .toList();
    }


    // Buscar un test por ID
    public Optional<TestDTO> obtenerPorId(Integer id) {
        return testRepositorio.findById(id)
                .map(TestDTO::new);
    }

    // Actualizar un test existente
    public TestDTO actualizarTest(Integer id, TestCrearDTO dto) {
        Test test = testRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID: " + id));

        Categoria categoria = categoriaRepositorio.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        test.setTitulo(dto.getTitulo());
        test.setDescripcion(dto.getDescripcion());
        test.setDificultad(dto.getDificultad());
        test.setCategoria(categoria);

        Test actualizado = testRepositorio.save(test);
        return new TestDTO(actualizado);
    }

    // liminar un test
    public void eliminarTest(Integer id) {
        testRepositorio.deleteById(id);
    }
}
