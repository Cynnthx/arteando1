package com.example.arteando1.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationDTO {
    private String token;
    private String mensaje;
    private String email;
    private Integer usuarioId;
    private String rol;            // "admin" o "cliente"
    private String nombreUsuario;
    private String nombreCompleto;


    public static AuthenticationDTO crearExito(String token, Integer id, String rol,
                                               String email, String nombreUsuario, String nombreCompleto, Integer clienteId) {
        return builder()
                .token(token)
                .mensaje("Éxito")
                .usuarioId(id)
                .rol(rol)
                .email(email)
                .nombreUsuario(nombreUsuario)
                .nombreCompleto(nombreCompleto)
                .build();

    }

    public static AuthenticationDTO crearError(String mensaje) {
        return builder()
                .mensaje(mensaje)
                .build();
    }
}
