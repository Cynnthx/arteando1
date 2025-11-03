package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaCrearDTO {
    private String texto;
    private String imagen;
    private Integer testId;
}
