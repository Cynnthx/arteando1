package com.example.arteando1.dtos;

import com.example.arteando1.modelos.Cliente;
import com.example.arteando1.modelos.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties({"cliente", "hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    public ClienteDTO(Cliente c) {
        this.id = c.getId();
        this.nombre = c.getNombre();
        this.apellidos = c.getApellidos();
        this.dni = c.getDni();
        this.foto = c.getFoto();
        this.direccion = c.getDireccion();
        this.usuario = c.getUsuario();
    }
}
