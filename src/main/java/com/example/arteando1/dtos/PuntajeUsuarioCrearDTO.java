package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuntajeUsuarioCrearDTO {
    private Integer id;
    private Integer puntaje;
    private Integer usuarioId;
    private Integer testId;

}
