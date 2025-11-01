package com.example.arteando1.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String email;
    private String nombreUsuario;
    private String rol;
    private String contrasena;
}
