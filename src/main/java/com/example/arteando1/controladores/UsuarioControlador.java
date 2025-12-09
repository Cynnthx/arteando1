package com.example.arteando1.controladores;

import com.example.arteando1.dtos.AuthenticationDTO;
import com.example.arteando1.dtos.RegistroDTO;
import com.example.arteando1.dtos.UsuarioDTO;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.servicios.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {
    private final UsuarioServicio usuarioServicio;

    // Registro de usuario (público)
    @PostMapping("/registro")
    public ResponseEntity<AuthenticationDTO> registrarUsuario(@RequestBody RegistroDTO dto) {
        return ResponseEntity.ok(usuarioServicio.registerDesdeRegistroDTO(dto));
    }


    //Login de usuario (público)
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(@RequestBody UsuarioDTO request) {
        Usuario usuario = usuarioServicio.buscarPorEmail(request.getEmail());
        if (usuario == null) {
            return ResponseEntity.ok(new AuthenticationDTO(null, "Usuario no encontrado"));
        }

        if (!usuario.getContrasena().equals(request.getContrasena())) {
            return ResponseEntity.ok(new AuthenticationDTO(null, "Contraseña no válida"));
        }

        // Generar token aquí si quieres
        String token = "tokenFalso";
        return ResponseEntity.ok(new AuthenticationDTO(token, "Login correcto"));
    }


    // Obtener perfil (requiere JWT válido)
    @PostMapping("/perfil")
    public ResponseEntity<UsuarioDTO> obtenerPerfil(@RequestBody UsuarioDTO request) {
        Usuario usuario = usuarioServicio.buscarPorEmail(request.getEmail());
        if (usuario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new UsuarioDTO(usuario));
    }




}
