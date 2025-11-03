package com.example.arteando1.servicios;

import com.example.arteando1.dtos.AuthenticationDTO;
import com.example.arteando1.dtos.UsuarioDTO;
import com.example.arteando1.enums.Rol;
import com.example.arteando1.modelos.Cliente;
import com.example.arteando1.modelos.TokenAcceso;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.ClienteRepositorio;
import com.example.arteando1.repositorios.UsuarioRepositorio;
import com.example.arteando1.seguridad.JwtServicio;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServicio implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepository;
    private final TokenAccesoServicio tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtServicio jwtServicio;
    private final ClienteRepositorio clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        return usuarioRepository.findTopByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public Usuario buscarUsuarioPorNickname(String nickname) {
        return usuarioRepository.findTopByNombreUsuario(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public Usuario guardarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.findTopByNombreUsuario(dto.getNombreUsuario()).isPresent()) {
            throw new IllegalArgumentException("El nickname ya está en uso");
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol(Rol.cliente);
        return usuarioRepository.save(usuario);
    }

    public AuthenticationDTO login(UsuarioDTO usuarioDTO) {
        Usuario usuario;
        try {
            usuario = (Usuario) loadUserByUsername(usuarioDTO.getNombreUsuario());
        } catch (UsernameNotFoundException e) {
            return AuthenticationDTO.builder()
                    .token(null)
                    .mensaje("Usuario no encontrado")
                    .build();
        }

        if (!validarContrasena(usuario, usuarioDTO.getContrasena())) {
            return AuthenticationDTO.builder()
                    .token(null)
                    .mensaje("Contraseña no válida")
                    .build();
        }

        String apiKey;
        if (usuario.getToken() == null || jwtServicio.isTokenExpired(usuario.getToken().getToken())) {
            apiKey = jwtServicio.generateToken(usuario, usuario.getId(), usuario.getRol().name());
            TokenAcceso token = Optional.ofNullable(usuario.getToken()).orElse(new TokenAcceso());
            token.setUsuario(usuario);
            token.setToken(apiKey);
            token.setExpiracion(LocalDateTime.now().plusDays(1));
            token.setEsValido(true);
            tokenService.save(token);
        } else {
            apiKey = usuario.getToken().getToken();
        }

        return AuthenticationDTO.builder()
                .token(apiKey)
                .mensaje("Login exitoso")
                .build();
    }

    public AuthenticationDTO register(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findTopByNombreUsuario(usuarioDTO.getNombreUsuario()).isPresent()) {
            return AuthenticationDTO.builder()
                    .token(null)
                    .mensaje("El nickname ya está en uso")
                    .build();
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        usuario.setRol(Rol.cliente);
        usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        clienteRepository.save(cliente);

        String jwtToken = jwtServicio.generateToken(usuario, usuario.getId(), usuario.getRol().name());
        return AuthenticationDTO.builder()
                .token(jwtToken)
                .mensaje("Registro exitoso")
                .build();
    }

    public boolean validarContrasena(Usuario usuario, String contrasenaSinEncriptar) {
        return passwordEncoder.matches(contrasenaSinEncriptar, usuario.getContrasena());
    }

    public Rol obtenerRolPorIdUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return usuario.getRol();
    }

    public Usuario getById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void updateUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
