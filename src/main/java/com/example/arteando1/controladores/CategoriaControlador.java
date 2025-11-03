package com.example.arteando1.controladores;

import com.example.arteando1.dtos.CategoriaDTO;
import com.example.arteando1.modelos.Categoria;
import com.example.arteando1.servicios.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaControlador {
    @Autowired
    private CategoriaServicio categoriaService;

    // Listar todas las categorías
    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<CategoriaDTO> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    // Obtener una categoría por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<CategoriaDTO> categoria = categoriaService.obtenerPorId(id);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva categoría
    @PostMapping("/crear")
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(categoriaDTO.getNombre());
        CategoriaDTO categoriaCreada = categoriaService.crearCategoria(nuevaCategoria);
        return ResponseEntity.ok(categoriaCreada);
    }

    // Editar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> editarCategoria(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());

        Optional<CategoriaDTO> categoriaEditada = categoriaService.editarCategoria(id, categoria);
        return categoriaEditada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
