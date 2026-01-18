package com.example.arteando1.dtos;

import com.example.arteando1.modelos.Opcion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcionDTO {
    private Integer id;
    private String texto;
    private Boolean esCorrecta;
    private Integer preguntaId;
    private String preguntaTexto;


    private String preguntaTestTitulo; // para devolver el título del test asociado a la pregunta


    public OpcionDTO(Opcion o) {
        this.id = o.getId();
        this.texto = o.getTexto();
        this.esCorrecta = o.getEsCorrecta();
        if (o.getPregunta() != null) {
            this.preguntaId = o.getPregunta().getId();
            this.preguntaTexto = o.getPregunta().getTexto();
            if (o.getPregunta().getTest() != null) {
                this.preguntaTestTitulo = o.getPregunta().getTest().getTitulo();
            }
        }
    }
}
