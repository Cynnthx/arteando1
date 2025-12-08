package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaConOpcionesDTO {
    private Integer id;
    private String texto;
    private String imagen;
    private List<OpcionDTO> opciones;
}
