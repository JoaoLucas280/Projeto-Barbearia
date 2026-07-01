package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Clientes", description = "Operações com clientes")
@RestController
@RequestMapping("/api/clientes/v1")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Buscar cliente por email", description = "Buscar os clientes com base no email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Email Inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @GetMapping("/email/{email}")
    public ClienteDTO buscaClientePorEmail(
            @Parameter(description = "Email utilizado e validado para encontrar o cliente") @PathVariable String email) {
        return clienteService.getClienteByEmail(email);
    }

    @Operation(summary = "Buscar cliente por id", description = "Buscar os clientes com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "id Inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscaClientePorId(
            @Parameter(description = "Id utilizado para encontrar o cliente") @PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @Operation(summary = "Buscar todos os clientes", description = "Buscar todos os clientes existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes Buscados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Email inválido ou em branco"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscaTodosClientes(
            @Parameter(description = "Email utilizado para buscar pelos clientes")@RequestParam(value = "email", required = false) String email) {
        if (email != null && !email.isBlank()) {
            return ResponseEntity.ok(List.of(clienteService.getClienteByEmail(email)));
        }
        return ResponseEntity.ok(clienteService.getAllClients());
    }

    @Operation(summary = "Atualizar cliente", description = "Atualizar os dados de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dado Inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizaCliente(
            @Parameter(description = "Id utilizado para confirmar que após a atualização permanecerá o mesmo")@PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados DTO utilizados para atualizar o Cliente") @Valid @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        ClienteDTO updated = clienteService.updateCliente(clienteDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deleta Cliente", description = "Deletar um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Id Inválido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(
            @Parameter(description = "Id utilizado para identificar o cliente que será deletado") @PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
