package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "categorias", schema = "app_arteando")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length=50)
    private String nombre;

}
