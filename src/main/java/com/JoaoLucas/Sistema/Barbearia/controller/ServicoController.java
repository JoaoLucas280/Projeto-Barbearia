package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.dto.ServicoDTO;
import com.JoaoLucas.Sistema.Barbearia.service.ClienteService;
import com.JoaoLucas.Sistema.Barbearia.service.ServicoService;
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

@Tag(name = "Serviços", description = "Operações com serviços")
@RestController
@RequestMapping("/api/servicos/v1")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService service;

    @Operation(summary = "Buscar serviço por id", description = "Buscar os serviços com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
            @ApiResponse(responseCode = "400", description = "Id Inválido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscaServicoPorId(
            @Parameter(description = "Id utilizado para encontrar o serviço") @PathVariable Long id) {
        return ResponseEntity.ok(service.getServicoById(id));
    }

    @Operation(summary = "Buscar serviço por email", description = "Buscar todos os serviços existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Comando inválido")
    })
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> buscaTodosServicos() {
        return ResponseEntity.ok(service.getAllServicos());
    }

    @Operation(summary = "Cria Serviço", description = "Criar um novo serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @PostMapping
    public ResponseEntity<ServicoDTO> criaServico(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados recebidos para criação do serviço")
            @Valid @RequestBody ServicoDTO servicoDTO) {
        ServicoDTO created = service.saveServico(servicoDTO);
        URI location = URI.create(String.format("/api/servicos/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Atualiza serviço", description = "Atualiza dados de um serviço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dado Inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizaServico(
            @Parameter(description = "Id do serviço a ser atualizado") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados recebidos para atualização do serviço") @Valid @RequestBody ServicoDTO servicoDTO) {
        servicoDTO.setId(id);
        ServicoDTO updated = service.updateServico(servicoDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deletar Serviço", description = "Deletar serviço utilizando id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
            @ApiResponse(responseCode = "400", description = "Id Inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(
            @Parameter(description = "Id do serviço a ser deletado") @PathVariable Long id) {
        service.deleteServico(id);
        return ResponseEntity.noContent().build();
    }
}
