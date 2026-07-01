package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.BarbeiroDTO;
import com.JoaoLucas.Sistema.Barbearia.service.BarbeiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Barbeiro", description = "Operações com barbeiro")
@RestController
@RequestMapping("/api/barbeiro/v1")
@RequiredArgsConstructor
public class BarbeiroController {


    private final BarbeiroService service;

    @Operation(summary = "Buscar Barbeiro", description = "Método que busca o barbeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbeiro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Barbeiro Não encontrado"),
    })
    @GetMapping
    public ResponseEntity<BarbeiroDTO> buscaBarbeiro() {
        return ResponseEntity.ok(service.getBarbeiro());
    }

    @Operation(summary = "Criar Barbeiro", description = "Método que cria o barbeiro (Somente um)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Barbeiro criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Barbeiro já existente"),
            @ApiResponse(responseCode = "400", description = "Dado inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
        })
    @PostMapping
    public ResponseEntity<BarbeiroDTO> criaBarbeiro(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recebe um corpo DTO com todas as informações que serão processadas do barbeiro")
            @Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        BarbeiroDTO created = service.saveBarbeiro(barbeiroDTO);
        URI location = URI.create(String.format("/api/barbeiro/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Atualiza Barbeiro", description = "Método que atualiza os dados do barbeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Barbeiro Não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dado inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
         })
    @PutMapping
    public ResponseEntity<BarbeiroDTO> atualizaBarbeiro(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recebe o body criado no formato DTO e atualiza") @Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        BarbeiroDTO updated = service.updateBarbeiro(barbeiroDTO);
        return ResponseEntity.ok(updated);
    }
}
