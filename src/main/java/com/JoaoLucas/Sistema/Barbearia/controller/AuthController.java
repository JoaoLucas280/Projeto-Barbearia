package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.LoginDTO;
import com.JoaoLucas.Sistema.Barbearia.dto.TokenResponseDTO;
import com.JoaoLucas.Sistema.Barbearia.service.AuthService;
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

@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
@Tag(name = "Login", description = "Login que gera o token para validação")
public class AuthController {
    private final AuthService service;

    @Operation(summary = "Método de login", description = "Método que gera o token para validação na hora do login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, token retornado"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (usuário ou senha incorretos)"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (campos obrigatórios ausentes)")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trás o DTO da classe login") @Valid @RequestBody LoginDTO dto) {
        String token = service.login(dto);
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(token);
        return ResponseEntity.ok(tokenResponseDTO);
    }
}
