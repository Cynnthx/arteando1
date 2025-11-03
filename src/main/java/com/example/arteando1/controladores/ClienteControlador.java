package com.example.arteando1.controladores;


import com.example.arteando1.dtos.ClienteDTO;
import com.example.arteando1.dtos.CrearClienteDTO;
import com.example.arteando1.servicios.ClienteServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteControlador {
    private final ClienteServicio clienteServicio;

    // Obtener todos los clientes
    @GetMapping("/all")
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteServicio.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
        Optional<ClienteDTO> cliente = clienteServicio.findById(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    // Crear un nuevo cliente
    @PostMapping("/crear")
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody CrearClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteServicio.crearCliente(clienteDTO);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    // Actualizar un cliente existente
    @PutMapping("/{clienteId}")
    public ClienteDTO editarCliente(@PathVariable Integer clienteId, @RequestBody @Valid ClienteDTO dto) throws Exception {
        ClienteDTO clienteDTO = clienteServicio.actualizarCliente(clienteId, dto);
        return clienteDTO;
    }

    // Eliminar un cliente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        clienteServicio.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
