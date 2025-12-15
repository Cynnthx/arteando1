package com.example.arteando1.dtos;

import com.example.arteando1.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO {
    private String nombreUsuario;
    private String email;
    private String contrasena;

    private Rol rol;
}
