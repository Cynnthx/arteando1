package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String foto;
    private String email;
    private String contrasena;
    // No hace falta rol, siempre se registran como cliente
}
