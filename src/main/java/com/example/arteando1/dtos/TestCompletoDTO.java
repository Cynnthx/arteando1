package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCompletoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String dificultad;
    private Integer categoriaId;
    private List<PreguntaConOpcionesDTO> preguntas;
}
