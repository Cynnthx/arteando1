package com.example.arteando1.servicios;

import com.example.arteando1.dtos.ClienteDTO;
import com.example.arteando1.dtos.CrearClienteDTO;
import com.example.arteando1.dtos.ImagenDTO;
import com.example.arteando1.modelos.Cliente;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.ClienteRepositorio;
import com.example.arteando1.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    // --------------------------
    // Obtener la imagen de perfil del cliente por usuarioId
    // --------------------------
    public ImagenDTO getImagenById(Integer usuarioId) {
        Cliente cliente = clienteRepositorio.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario ID: " + usuarioId));
        return new ImagenDTO(cliente.getFoto());
    }

    // --------------------------
    // Obtener todos los clientes
    // --------------------------
    public List<ClienteDTO> findAll() {
        return clienteRepositorio.findAll().stream()
                .map(ClienteDTO::new)
                .toList();
    }

    // --------------------------
    // Obtener cliente por ID
    // --------------------------
    public Optional<ClienteDTO> findById(Integer id) {
        return clienteRepositorio.findById(id)
                .map(ClienteDTO::new);
    }

    // --------------------------
    // Crear cliente + usuario
    // --------------------------
    @Transactional
    public ClienteDTO crearCliente(CrearClienteDTO dto) {

        // Crear entidad Cliente
        Cliente cliente = dto.toEntity();

        // Crear usuario asociado
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol());

        usuarioRepositorio.save(usuario);

        // Asociar usuario al cliente
        cliente.setUsuario(usuario);

        Cliente clienteGuardado = clienteRepositorio.save(cliente);

        return new ClienteDTO(clienteGuardado);
    }

    // --------------------------
    // Actualizar cliente y datos del usuario
    // --------------------------
    @Transactional
    public ClienteDTO actualizarClientePerfil(Integer usuarioId, CrearClienteDTO dto) {

        Cliente cliente = clienteRepositorio.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario ID: " + usuarioId));

        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setDni(dto.getDni());
        cliente.setFoto(dto.getFoto());
        cliente.setDireccion(dto.getDireccion());

        Usuario usuario = cliente.getUsuario();
        if (usuario != null) {
            usuario.setEmail(dto.getEmail());
            usuario.setNombreUsuario(dto.getNombreUsuario());
            usuario.setContrasena(dto.getContrasena());
            usuarioRepositorio.save(usuario);
        }

        Cliente clienteActualizado = clienteRepositorio.save(cliente);

        return new ClienteDTO(clienteActualizado);
    }

    // --------------------------
    // Obtener cliente completo por usuarioId
    // --------------------------
    public ClienteDTO getClientePerfil(Integer usuarioId) {
        Cliente cliente = clienteRepositorio.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario ID: " + usuarioId));
        return new ClienteDTO(cliente);
    }

    // --------------------------
    // Eliminar cliente
    // --------------------------
    public void eliminarCliente(Integer id) {
        clienteRepositorio.deleteById(id);
    }


}
