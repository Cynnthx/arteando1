package com.example.arteando1.dtos;

import com.example.arteando1.modelos.Pregunta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDTO {
    private Integer id;
    private String texto;
    private String imagen;
    private Integer testId;
    private String testTitulo;

    public PreguntaDTO(Pregunta p) {
        if (p == null) return;
        this.id = p.getId();
        this.texto = p.getTexto();
        this.imagen = p.getImagen();
        if (p.getTest() != null) {
            this.testId = p.getTest().getId();
            this.testTitulo = p.getTest().getTitulo();
        }
    }
}
