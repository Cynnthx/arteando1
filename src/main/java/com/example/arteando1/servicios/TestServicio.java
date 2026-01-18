package com.example.arteando1.servicios;


import com.example.arteando1.dtos.*;
import com.example.arteando1.modelos.Categoria;
import com.example.arteando1.modelos.Opcion;
import com.example.arteando1.modelos.Pregunta;
import com.example.arteando1.modelos.Test;
import com.example.arteando1.repositorios.CategoriaRepositorio;
import com.example.arteando1.repositorios.OpcionRepositorio;
import com.example.arteando1.repositorios.PreguntaRepositorio;
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
    private final OpcionRepositorio opcionRepositorio;
    private final PreguntaRepositorio preguntaRepositorio;

    // Crear un nuevo test
    public TestDTO crearTest(Test test) {

        // Validación básica (ejemplo)
        if (test.getTitulo() == null || test.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título del test es obligatorio");
        }

        if (test.getCategoria() == null) {
            throw new IllegalArgumentException("El test debe tener una categoría");
        }

        return new TestDTO(testRepositorio.save(test));
    }

    // Actualizar un test
    public Optional<TestDTO> actualizarTest(Integer id, Test testDetalles) {
        return testRepositorio.findById(id)
                .map(test -> {
                    test.setTitulo(testDetalles.getTitulo());
                    test.setDescripcion(testDetalles.getDescripcion());
                    test.setDificultad(testDetalles.getDificultad());
                    test.setCategoria(testDetalles.getCategoria());

                    return new TestDTO(testRepositorio.save(test));
                });
    }

    // Eliminar un test
    public void eliminarTest(Integer id) {

        // Primero eliminar preguntas asociadas
        preguntaRepositorio.deleteByTestId(id);

        testRepositorio.deleteById(id);
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

    public TestCompletoDTO obtenerTestCompleto(Integer testId) {
        Test test = testRepositorio.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test no encontrado con ID " + testId));

        List<Pregunta> preguntas = preguntaRepositorio.findByTestId(testId);

        List<PreguntaConOpcionesDTO> preguntasDTO = preguntas.stream().map(p -> {
            List<Opcion> opciones = opcionRepositorio.findByPreguntaId(p.getId());

            return new PreguntaConOpcionesDTO(
                    p.getId(),
                    p.getTexto(),
                    p.getImagen(),
                    opciones.stream().map(OpcionDTO::new).toList()
            );
        }).toList();

        return new TestCompletoDTO(
                test.getId(),
                test.getTitulo(),
                test.getDescripcion(),
                test.getDificultad(),
                test.getCategoria().getId(),
                preguntasDTO
        );
    }


}
