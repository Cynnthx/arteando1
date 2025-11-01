package com.example.arteando1.servicios;

import com.example.arteando1.modelos.TokenAcceso;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.TokenAccesoRepositorio;
import com.example.arteando1.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenAccesoServicio {
    @Autowired
    private TokenAccesoRepositorio tokenAccesoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    public TokenAcceso crearTokenParaUsuario(int usuarioId) {
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear el token
        TokenAcceso token = TokenAcceso.builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                .expiracion(LocalDateTime.now().plusHours(1))
                .esValido(true)
                .usuario(usuario)
                .build();

        // Guardar el token en la base de datos
        return tokenAccesoRepositorio.save(token);
    }

    public void save(TokenAcceso token) {
        tokenAccesoRepositorio.save(token);
    }
}
