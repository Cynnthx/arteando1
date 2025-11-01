package com.example.arteando1.dtos;

import com.example.arteando1.modelos.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearClienteDTO {

    private String nombre;
    private String apellidos;
    private String dni;
    private String foto;
    private String direccion;

    // Datos del Usuario (para creación)
    private String email;
    private String nickname;
    private String contrasena;

    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setNombre(this.nombre);
        cliente.setApellidos(this.apellidos);
        cliente.setDni(this.dni);
        cliente.setFoto(this.foto);
        cliente.setDireccion(this.direccion);
        return cliente;
    }


}
