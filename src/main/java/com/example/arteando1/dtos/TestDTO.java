package com.example.arteando1.dtos;

import com.example.arteando1.modelos.Test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String dificultad;
    private String categoriaNombre; // para devolver el nombre

    public TestDTO(Test t) {
        if (t == null) return;

        this.id = t.getId();
        this.titulo = t.getTitulo();
        this.descripcion = t.getDescripcion();
        this.dificultad = t.getDificultad();

        if (t.getCategoria() != null) {
            this.categoriaNombre = t.getCategoria().getNombre();
        }
    }
}
