package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCrearDTO {
    private String titulo;
    private String descripcion;
    private String dificultad;
    private Integer categoriaId;
}
