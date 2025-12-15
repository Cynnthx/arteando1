package com.example.arteando1.controladores;

import com.example.arteando1.dtos.*;
import com.example.arteando1.servicios.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    // Registro público para clientes
    @PostMapping("/registro/cliente")
    public ResponseEntity<AuthenticationDTO> registrarCliente(@RequestBody RegistroDTO registroDTO) {
        return ResponseEntity.ok(usuarioServicio.registerDesdeRegistroDTO(registroDTO));
    }

    // Registro para administradores
    @PostMapping("/registro/admin")
    public ResponseEntity<AuthenticationDTO> registrarAdmin(@RequestBody AdminDTO registroDTO) {
        return ResponseEntity.ok(usuarioServicio.registrarAdmin(registroDTO));
    }

    // Login público
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(usuarioServicio.login(loginDTO));
    }

    // Obtener perfil del usuario autenticado
    @GetMapping("/perfil")
    public ResponseEntity<CrearClienteDTO> obtenerPerfil() {
        return ResponseEntity.ok(usuarioServicio.obtenerPerfilUsuario());
    }


    // Actualizar perfil
    @PutMapping("/perfil")
    public ResponseEntity<CrearClienteDTO> actualizarPerfil(@RequestBody ActualizarPerfil request) {
        return ResponseEntity.ok(usuarioServicio.actualizarPerfil(request));
    }

}
