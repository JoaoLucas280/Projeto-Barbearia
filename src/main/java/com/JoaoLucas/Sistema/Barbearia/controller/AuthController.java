package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.LoginDTO;
import com.JoaoLucas.Sistema.Barbearia.dto.TokenResponseDTO;
import com.JoaoLucas.Sistema.Barbearia.service.AuthService;
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
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        String token = service.login(dto);
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(token);
        return ResponseEntity.ok(tokenResponseDTO);
    }
}
