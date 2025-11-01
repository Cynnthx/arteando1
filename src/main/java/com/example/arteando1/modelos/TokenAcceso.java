package com.example.arteando1.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "token_acceso", schema = "app_arteando", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"usuario"})
public class TokenAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token", nullable = false, length = 500, unique = true)
    @NotEmpty(message = "El token no puede estar vacío")
    private String token;

    // Tu tabla usa "expiracion"
    @Column(name = "expiracion", nullable = false)
    @NotNull(message = "La expiración no puede ser nula")
    private LocalDateTime expiracion;

    @Column(name = "es_valido", nullable = false)
    private boolean esValido = true;

    // columna usuarios_id en la tabla
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", nullable = false)
    @NotNull(message = "El usuario no puede ser nulo")
    private Usuario usuario;

    @Override
    public String toString() {
        return "TokenAcceso{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiracion=" + expiracion +
                ", esValido=" + esValido +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                '}';
    }
}
