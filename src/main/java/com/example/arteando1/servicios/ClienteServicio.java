package com.example.arteando1.servicios;

import com.example.arteando1.dtos.ClienteDTO;
import com.example.arteando1.dtos.CrearClienteDTO;
import com.example.arteando1.dtos.ImagenDTO;
import com.example.arteando1.modelos.Cliente;
import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.ClienteRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteServicio {


    private final ClienteRepositorio clienteRepository;
//    private final UsuarioServicio usuarioServicio;

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
    public ClienteDTO crearCliente(Cliente cliente) {
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return new ClienteDTO(clienteGuardado);
    }

    public Optional<ClienteDTO> actualizarCliente(Integer id, Cliente clienteDetails) {
        return clienteRepository.findById(id)
                .map(existingCliente -> {
                    existingCliente.setNombre(clienteDetails.getNombre());
                    existingCliente.setApellidos(clienteDetails.getApellidos());
                    existingCliente.setDni(clienteDetails.getDni());
                    existingCliente.setFoto(clienteDetails.getFoto());
                    existingCliente.setDireccion(clienteDetails.getDireccion());
                    return new ClienteDTO(clienteRepository.save(existingCliente));
                });
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


