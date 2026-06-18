package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes/v1")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/email/{email}")
    public ClienteDTO buscaClientePorEmail(@PathVariable String email) {
        return clienteService.getClienteByEmail(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscaClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscaTodosClientes(@RequestParam(value = "email", required = false) String email) {
        if (email != null && !email.isBlank()) {
            return ResponseEntity.ok(List.of(clienteService.getClienteByEmail(email)));
        }
        return ResponseEntity.ok(clienteService.getAllClients());
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criaCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO created = clienteService.saveCliente(clienteDTO);
        URI location = URI.create(String.format("/api/clientes/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizaCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        ClienteDTO updated = clienteService.updateCliente(clienteDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
