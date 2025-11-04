package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaUsuarioCrearDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer preguntaId;
    private Integer opcionId;
}
