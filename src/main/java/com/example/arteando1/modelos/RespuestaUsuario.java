package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "respuestas_usuario", schema = "arteando", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 🔹 Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;
    // 🔹 Relación con Pregunta
    @ManyToOne
    @JoinColumn(name = "preguntas_id", referencedColumnName = "id", nullable = false)
    private Pregunta pregunta;
    // 🔹 Relación con Opción (la elegida por el usuario)
    @ManyToOne
    @JoinColumn(name = "opciones_id", referencedColumnName = "id", nullable = false)
    private Opcion opcion;
}
