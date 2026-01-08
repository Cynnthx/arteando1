package com.example.arteando1.servicios;

import com.example.arteando1.dtos.*;
import com.example.arteando1.enums.Rol;
import com.example.arteando1.modelos.Cliente;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.ClienteRepositorio;
import com.example.arteando1.repositorios.UsuarioRepositorio;
import com.example.arteando1.seguridad.JwtServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServicio implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServicio jwtServicio;
    private final ClienteRepositorio clienteRepository;

    // Spring Security autentica por EMAIL
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    // Método para validar DNI/NIE
    public boolean esDniNieValido(String dniNie) {
        if (dniNie == null || dniNie.isBlank()) return false;

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        dniNie = dniNie.toUpperCase().trim();

        // NIE: sustituir letra inicial por número
        if (dniNie.matches("^[XYZ]\\d{7}[A-Z]$")) {
            char letraInicial = dniNie.charAt(0);
            String numero = switch (letraInicial) {
                case 'X' -> "0";
                case 'Y' -> "1";
                case 'Z' -> "2";
                default -> throw new IllegalStateException("Letra NIE inválida");
            };
            numero += dniNie.substring(1, 8);
            int resto = Integer.parseInt(numero) % 23;
            return dniNie.charAt(8) == letras.charAt(resto);
        }

        // DNI normal
        if (dniNie.matches("^\\d{8}[A-Z]$")) {
            int numero = Integer.parseInt(dniNie.substring(0, 8));
            int resto = numero % 23;
            return dniNie.charAt(8) == letras.charAt(resto);
        }

        return false;
    }


    // =========================
    // REGISTRO CLIENTE
    // =========================
    public AuthenticationDTO registerDesdeRegistroDTO(RegistroDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            return AuthenticationDTO.crearError("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol(Rol.cliente);

        String nombreUsuario = dto.getNombre().toLowerCase() + "." + dto.getApellidos().toLowerCase();
        usuario.setNombreUsuario(nombreUsuario);

        usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setDni(dto.getDni());
        cliente.setDireccion(dto.getDireccion());
        cliente.setFoto(dto.getFoto());
        cliente.setUsuario(usuario);

        clienteRepository.save(cliente);

        // Ajuste: usar la firma correcta de generateToken(UserDetails, Integer, String)
        String token = jwtServicio.generateToken(usuario, usuario.getId(), usuario.getRol().name());

        return AuthenticationDTO.crearExito(
                token,
                usuario.getId(),
                usuario.getRol().name(),
                usuario.getEmail(),
                usuario.getNombreUsuario(),
                cliente.getNombre() + " " + cliente.getApellidos(),
                cliente.getId()
        );
    }

    // =========================
    // LOGIN
    // =========================
    public AuthenticationDTO login(LoginDTO loginRequest) {

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(loginRequest.getContrasena(), usuario.getContrasena())) {
            return AuthenticationDTO.crearError("Credenciales inválidas");
        }

        // Ajuste: usar la firma correcta de generateToken(UserDetails, Integer, String)
        String token = jwtServicio.generateToken(usuario, usuario.getId(), usuario.getRol().name());

        String nombreCompleto = null;
        Integer clienteId = null;

        if (usuario.getRol() == Rol.cliente) {
            Optional<Cliente> clienteOpt = clienteRepository.findByUsuario(usuario);
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                nombreCompleto = cliente.getNombre() + " " + cliente.getApellidos();
                clienteId = cliente.getId();
            }
        }

        return AuthenticationDTO.crearExito(
                token,
                usuario.getId(),
                usuario.getRol().name(),
                usuario.getEmail(),
                usuario.getNombreUsuario(),
                nombreCompleto,
                clienteId
        );
    }

    // =========================
    // REGISTRO ADMIN
    // =========================
    public AuthenticationDTO registrarAdmin(AdminDTO registroDTO) {

        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            return AuthenticationDTO.crearError("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(registroDTO.getEmail());
        usuario.setNombreUsuario(registroDTO.getNombre());
        usuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        usuario.setRol(Rol.admin);

        usuarioRepository.save(usuario);

        // Ajuste: usar la firma correcta de generateToken(UserDetails, Integer, String)
        String token = jwtServicio.generateToken(usuario, usuario.getId(), usuario.getRol().name());

        return AuthenticationDTO.crearExito(
                token,
                usuario.getId(),
                usuario.getRol().name(),
                usuario.getEmail(),
                usuario.getNombreUsuario(),
                null,
                null
        );
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = auth.getName(); // Spring Security usa el email como nombre de usuario

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }


    public CrearClienteDTO obtenerPerfilUsuario() {
        Usuario usuario = obtenerUsuarioAutenticado();

        Cliente cliente = clienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Perfil de cliente no encontrado"));

        CrearClienteDTO perfil = new CrearClienteDTO();

        // Datos cliente
        perfil.setNombre(cliente.getNombre());
        perfil.setApellidos(cliente.getApellidos());
        perfil.setDni(cliente.getDni());
        perfil.setDireccion(cliente.getDireccion());
        perfil.setFoto(cliente.getFoto());

        // Datos usuario
        perfil.setEmail(usuario.getEmail());
        perfil.setNombreUsuario(usuario.getNombreUsuario());

        return perfil;
    }

    @Transactional
    public CrearClienteDTO actualizarPerfil(ActualizarPerfil request) {
        Usuario usuario = obtenerUsuarioAutenticado();

        Cliente cliente = clienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        if (!esDniNieValido(request.getDni())) {
            throw new IllegalArgumentException("DNI o NIE no válido");
        }

        cliente.setNombre(request.getNombre());
        cliente.setApellidos(request.getApellidos());
        cliente.setDireccion(request.getDireccion());
        cliente.setFoto(request.getFoto());
        cliente.setDni(request.getDni());

        clienteRepository.save(cliente);

        CrearClienteDTO dto = new CrearClienteDTO();
        dto.setNombre(cliente.getNombre());
        dto.setApellidos(cliente.getApellidos());
        dto.setDni(cliente.getDni());
        dto.setDireccion(cliente.getDireccion());
        dto.setFoto(cliente.getFoto());
        dto.setEmail(usuario.getEmail());
        dto.setNombreUsuario(usuario.getNombreUsuario());

        return dto;
    }

    public Usuario crearUsuario(CrearUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol(dto.getRol());
        return usuarioRepository.save(usuario);
    }


    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    @Transactional
    public void borrarCliente(Integer id) {
        clienteRepository.findById(id)
                .ifPresent(clienteRepository::delete);
    }





}
