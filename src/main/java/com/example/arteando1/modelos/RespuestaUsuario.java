package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "respuestas_usuario", schema = "app_arteando")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "preguntas_id", referencedColumnName = "id", nullable = false)
    private Pregunta pregunta;
    @ManyToOne
    @JoinColumn(name = "opciones_id", referencedColumnName = "id", nullable = false)
    private Opcion opcion;
}
