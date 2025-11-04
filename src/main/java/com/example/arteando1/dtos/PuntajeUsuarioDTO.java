package com.example.arteando1.dtos;

import com.example.arteando1.modelos.PuntajeUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuntajeUsuarioDTO {
    private Integer id;
    private Integer puntaje;

    private Integer usuarioId;
    private String nombreUsuario;
    private String emailUsuario;

    private Integer testId;
    private String tituloTest;
    private String dificultadTest;
    private String categoriaTest;

    public PuntajeUsuarioDTO(PuntajeUsuario entidad) {
        if (entidad == null) return;

        this.id = entidad.getId();
        this.puntaje = entidad.getPuntaje();

        // Evita NullPointerException si usuario o test son nulos
        if (entidad.getUsuario() != null) {
            this.usuarioId = entidad.getUsuario().getId();
            this.nombreUsuario = entidad.getUsuario().getNombreUsuario();
            this.emailUsuario = entidad.getUsuario().getEmail();
        }

        if (entidad.getTest() != null) {
            this.testId = entidad.getTest().getId();
            this.tituloTest = entidad.getTest().getTitulo();
            this.dificultadTest = entidad.getTest().getDificultad();

            if (entidad.getTest().getCategoria() != null) {
                this.categoriaTest = entidad.getTest().getCategoria().getNombre();
            }
        }
    }
}
