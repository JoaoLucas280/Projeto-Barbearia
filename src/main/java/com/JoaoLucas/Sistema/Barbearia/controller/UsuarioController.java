package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.UsuarioDTO;
import com.JoaoLucas.Sistema.Barbearia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "Usuario", description = "Operações com o usuário")
@RestController
@RequestMapping("/api/usuarios/v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criar Usuario", description = "Método que cria o usuário (somente um)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Usuário já existente"),
            @ApiResponse(responseCode = "400", description = "Dado inválido"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão de acesso")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recebe um corpo DTO de usuario para a criação")
            @Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO created = usuarioService.criarAdmin(dto);
        URI location = URI.create(String.format("/api/usuario/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }
}
