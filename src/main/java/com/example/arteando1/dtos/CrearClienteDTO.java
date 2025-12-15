package com.example.arteando1.dtos;

import com.example.arteando1.enums.Rol;
import com.example.arteando1.modelos.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearClienteDTO {

    // Datos del Cliente
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;


    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;


    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "DNI no válido (8 números + 1 letra)")
    private String dni;

    @Size(max = 255, message = "La URL de la foto no puede exceder 255 caracteres")
    private String foto;


    @Size(max = 100, message = "La dirección no puede exceder 100 caracteres")
    private String direccion;


    // Datos del Usuario
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    @NotBlank(message = "El nickname es obligatorio")
    @Size(min = 4, max = 20, message = "Nickname debe tener entre 4 y 20 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    private String contrasena;

    // Campo Rol con valor por defecto
    @NotNull(message = "El rol es obligatorio")
    private Rol rol = Rol.cliente; // Valor por defecto para nuevos clientes


    // Método para convertir a entidad Cliente
    public Cliente toEntity() {
        return Cliente.builder()
                .nombre(this.nombre.trim())
                .apellidos(this.apellidos.trim())
                .dni(this.dni.trim().toUpperCase()) // Normaliza el DNI
                .foto(this.foto != null ? this.foto.trim() : null)
                .direccion(this.direccion != null ? this.direccion.trim() : null)
                .build();
    }


}
