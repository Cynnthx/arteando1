package com.example.arteando1.modelos;

import jakarta.persistence.*;
import lombok.*;

//, catalog = "postgres"
@Entity
@Table(name = "clientes", schema = "app_arteando")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length=50)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length=100)
    private String apellidos;

    @Column(name = "dni", nullable = false, length=9, unique = true)
    private String dni;

    @Column(name = "foto", length=255)
    private String foto;

    @Column(name = "direccion", length=150)
    private String direccion;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;
}
