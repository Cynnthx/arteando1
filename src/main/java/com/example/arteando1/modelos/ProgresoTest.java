package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "progreso_test", schema = "app_arteando")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgresoTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "porcentaje_completado", nullable = false)
    private Integer porcentajeCompletado;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)
    private Test test;
}
