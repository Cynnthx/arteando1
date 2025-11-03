package com.example.arteando1.servicios;

import com.example.arteando1.dtos.CategoriaDTO;
import com.example.arteando1.modelos.Categoria;
import com.example.arteando1.repositorios.CategoriaRepositorio;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class CategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepository;

    //Listar todas las categorías
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaDTO::new) // Convierte cada Categoria en CategoriaDTO
                .collect(Collectors.toList());
    }


    /**
     * Crear una nueva categoría.
     */
    public CategoriaDTO crearCategoria(Categoria categoria) {
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return new CategoriaDTO(categoriaGuardada);
    }

    //Obtener una categoría por su ID
    public Optional<CategoriaDTO> obtenerPorId(Integer id) {
        return categoriaRepository.findById(id)
                .map(CategoriaDTO::new);
    }


    /**
     * Editar una categoría existente.
     */
    public Optional<CategoriaDTO> editarCategoria(Integer id, Categoria categoriaDetalles) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaDetalles.getNombre());
                    Categoria categoriaActualizada = categoriaRepository.save(categoria);
                    return new CategoriaDTO(categoriaActualizada);
                });
    }

    /**
     * Eliminar una categoría.
     */
    public void eliminarCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
