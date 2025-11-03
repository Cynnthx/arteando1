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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteServicio {


    private final ClienteRepositorio clienteRepository;
    private final UsuarioRepositorio usuarioRepositorio;

    // Obtener la imagen de perfil del cliente
    public ImagenDTO getImagenById(Integer id) {
        Cliente cliente = clienteRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario ID: " + id));
        return new ImagenDTO(cliente.getFoto());
    }

    //Todos los clientes
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteDTO::new)
                .toList();
    }

    // Buscar cliente por ID
    public Optional<ClienteDTO> findById(Integer id) {
        return clienteRepository.findById(id)
                .map(ClienteDTO::new);
    }

    // Crear un nuevo cliente
    public ClienteDTO crearCliente(CrearClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellidos(clienteDTO.getApellidos());
        cliente.setDni(clienteDTO.getDni());
        cliente.setFoto(clienteDTO.getFoto());
        cliente.setDireccion(clienteDTO.getDireccion());

        // Asociar usuario si existe
        if (clienteDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepositorio.findById(clienteDTO.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + clienteDTO.getUsuarioId()));
            cliente.setUsuario(usuario);
        }

        Cliente clienteGuardado = clienteRepository.save(cliente);
        return new ClienteDTO(clienteGuardado);
    }

    public ClienteDTO actualizarCliente(Integer id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setDni(dto.getDni());
        cliente.setFoto(dto.getFoto());
        cliente.setDireccion(dto.getDireccion());

        Cliente clienteActualizado = clienteRepository.save(cliente);

        return new ClienteDTO(clienteActualizado);
    }


    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }



    // Obtener perfil completo del cliente
    public ClienteDTO getClientePerfil(Integer id) {
        Cliente cliente = clienteRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Usuario usuario = cliente.getUsuario();

        return new ClienteDTO(cliente);

    }
//
//    public Optional<ClienteDTO> actualizarClientePerfil(Integer id, CrearClienteDTO clienteDTO) {
//        return clienteRepository.findByUsuarioId(id)
//                .map(cliente -> {
//                    cliente.setFoto(clienteDTO.getFoto());
//                    cliente.setDni(clienteDTO.getDni());
//                    cliente.setNombre(clienteDTO.getNombre());
//                    cliente.setApellidos(clienteDTO.getApellidos());
//                    cliente.setDireccion(clienteDTO.getDireccion());
//
//                    Usuario usuario = cliente.getUsuario();
//                    usuario.setEmail(clienteDTO.getUsuario().getEmail());
//                    usuario.setNombreUsuario(clienteDTO.getUsuario().getNickname());
//                    usuarioServicio.actualizarUsuario(usuario);
//
//                    return new ClienteDTO(clienteRepository.save(cliente));
//                });
//    }
}


