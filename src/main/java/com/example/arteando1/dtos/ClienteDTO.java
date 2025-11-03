package com.example.arteando1.dtos;

import com.example.arteando1.modelos.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String foto;
    private String direccion;

    // Datos del Usuario
    private String email;
    private String nickname;



    public ClienteDTO(Cliente c) {
        if (c == null) return;

        this.id = c.getId();
        this.nombre = c.getNombre();
        this.apellidos = c.getApellidos();
        this.dni = c.getDni();
        this.foto = c.getFoto();
        this.direccion = c.getDireccion();

        if (c.getUsuario() != null) {
            this.email = c.getUsuario().getEmail();
            this.nickname = c.getUsuario().getNombreUsuario();
        }
    }
}
