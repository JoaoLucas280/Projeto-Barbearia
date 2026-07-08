package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.LoginDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Usuario;
import com.JoaoLucas.Sistema.Barbearia.repository.UsuarioRepository;
import com.JoaoLucas.Sistema.Barbearia.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService service;
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public String login(LoginDTO dto) {
        Usuario usernameVerficado = repository.findByUsername(dto.getUsername()).orElseThrow
                (() -> new BadCredentialsException("Usuário ou Senha inválidos!"));
        var senhaComparada = encoder.matches(dto.getSenha(), usernameVerficado.getSenha());
        if (!senhaComparada) throw new BadCredentialsException("Usuário ou Senha inválidos!");
        return service.gerarToken(usernameVerficado.getUsername());
    }
}
