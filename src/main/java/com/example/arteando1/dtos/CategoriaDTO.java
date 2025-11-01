package com.example.arteando1.dtos;

import com.example.arteando1.modelos.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Integer id;
    private String nombre;

    public CategoriaDTO(Categoria c) {
        this.id = c.getId();
        this.nombre = c.getNombre();
    }
}
