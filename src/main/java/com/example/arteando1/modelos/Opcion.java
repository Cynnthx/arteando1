package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "opciones", schema = "arteando", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Opcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "texto", nullable = false, length = 200)
    private String texto;

    // Usamos boolean para facilitar la lógica en Java/Frontend
    @Column(name = "es_correcta", nullable = false)
    private Boolean esCorrecta;

    // 🔹 Relación con Pregunta
    @ManyToOne
    @JoinColumn(name = "preguntas_id", referencedColumnName = "id", nullable = false)
    private Pregunta pregunta;
}
