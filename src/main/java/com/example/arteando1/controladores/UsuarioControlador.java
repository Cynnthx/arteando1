package com.example.arteando1.controladores;

import com.example.arteando1.dtos.AuthenticationDTO;
import com.example.arteando1.dtos.UsuarioDTO;
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
    public ResponseEntity<AuthenticationDTO> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioServicio.register(usuarioDTO));
    }

    //Login de usuario (público)
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioServicio.login(usuarioDTO));
    }

    // Obtener perfil (requiere JWT válido)
    @GetMapping("/perfil")
    public ResponseEntity<String> obtenerPerfil() {
        // ⚠️ Aquí luego puedes devolver un DTO con datos del usuario autenticado
        return ResponseEntity.ok("Perfil del usuario autenticado correctamente ✅");
    }

}
