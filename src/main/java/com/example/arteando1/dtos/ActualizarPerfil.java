package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarPerfil {
    private String nombre;
    private String apellidos;
    private String dni;
    private String foto;
    private String direccion;

}
