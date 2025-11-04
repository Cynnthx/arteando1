package com.example.arteando1.dtos;

import com.example.arteando1.modelos.RespuestaUsuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RespuestaUsuarioDTO {
    private Integer id;

    private Integer usuarioId;
    private String nombreUsuario;
    private String emailUsuario;

    private Integer preguntaId;
    private String textoPregunta;
    private String tituloTest;

    private Integer opcionId;
    private String textoOpcion;
    private Boolean esCorrecta;

    public RespuestaUsuarioDTO(RespuestaUsuario entidad) {
        if (entidad == null) return;

        this.id = entidad.getId();

        if (entidad.getUsuario() != null) {
            this.usuarioId = entidad.getUsuario().getId();
            this.nombreUsuario = entidad.getUsuario().getNombreUsuario();
            this.emailUsuario = entidad.getUsuario().getEmail();
        }

        if (entidad.getPregunta() != null) {
            this.preguntaId = entidad.getPregunta().getId();
            this.textoPregunta = entidad.getPregunta().getTexto();

            if (entidad.getPregunta().getTest() != null) {
                this.tituloTest = entidad.getPregunta().getTest().getTitulo();
            }
        }

        if (entidad.getOpcion() != null) {
            this.opcionId = entidad.getOpcion().getId();
            this.textoOpcion = entidad.getOpcion().getTexto();
            this.esCorrecta = entidad.getOpcion().getEsCorrecta();
        }
    }
}
