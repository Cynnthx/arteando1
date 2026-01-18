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


    public TestDTO (Test test) {
        this.id = test.getId();
        this.titulo = test.getTitulo();
        this.descripcion = test.getDescripcion();
        this.dificultad = test.getDificultad();
        if (test.getCategoria() != null) {
            this.categoriaNombre = test.getCategoria().getNombre();
        }
    }

}
