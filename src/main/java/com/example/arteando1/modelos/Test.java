package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "test", schema = "arteando", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="titulo")
    private String titulo;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="dificultad")
    private String dificultad;

    // 🔹 Relación con la tabla 'categorias'
    @ManyToOne
    @JoinColumn(name = "categorias_id", referencedColumnName = "id")
    private Categoria categoria;

}
