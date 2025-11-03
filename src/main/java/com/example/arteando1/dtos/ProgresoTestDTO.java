package com.example.arteando1.dtos;

import com.example.arteando1.modelos.ProgresoTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoTestDTO {
    private Integer id;
    private Integer porcentajeCompletado;
    private Integer usuarioId;
    private String nombreUsuario;
    private Integer testId;
    private String tituloTest;

    public ProgresoTestDTO(ProgresoTest p) {
        if (p == null) return;
        this.id = p.getId();
        this.porcentajeCompletado = p.getPorcentajeCompletado();
        if (p.getUsuario() != null) {
            this.usuarioId = p.getUsuario().getId();
            this.nombreUsuario = p.getUsuario().getNombreUsuario();
        }
        if (p.getTest() != null) {
            this.testId = p.getTest().getId();
            this.tituloTest = p.getTest().getTitulo();
        }
    }
}
