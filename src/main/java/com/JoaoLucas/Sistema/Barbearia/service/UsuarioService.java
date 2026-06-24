package com.JoaoLucas.Sistema.Barbearia.service;


import com.JoaoLucas.Sistema.Barbearia.dto.UsuarioDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Usuario;
import com.JoaoLucas.Sistema.Barbearia.entity.enums.Role;
import com.JoaoLucas.Sistema.Barbearia.mapper.ObjectMapper;
import com.JoaoLucas.Sistema.Barbearia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioDTO criarAdmin(UsuarioDTO dto) {
        if (usuarioRepository.count() > 0) throw new IllegalStateException("Já existe um ADMIN!");
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setRole(Role.ADMIN);
        usuario.setSenha(encoder.encode(dto.getSenha()));
        var saved = usuarioRepository.save(usuario);
        var converted = ObjectMapper.map(saved, UsuarioDTO.class);
        converted.setSenha(null);
        return converted;
    }
}
