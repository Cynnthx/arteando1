package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcionCrearDTO {
    private String texto;
    private Boolean esCorrecta;
    private Integer preguntaId;
}
