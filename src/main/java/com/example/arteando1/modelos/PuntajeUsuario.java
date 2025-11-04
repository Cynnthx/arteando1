package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "puntaje_usuario", schema = "app_arteando")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuntajeUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer puntaje;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)
    private Test test;
}
