package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoTestCrearDTO {
    private Integer porcentajeCompletado;
    private Integer usuarioId;
    private Integer testId;
}
