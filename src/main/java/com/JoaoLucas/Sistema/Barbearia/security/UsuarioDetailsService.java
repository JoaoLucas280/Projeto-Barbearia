package com.JoaoLucas.Sistema.Barbearia.security;

import com.JoaoLucas.Sistema.Barbearia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario =  usuarioRepository.findByUsername(username).orElseThrow
                (() -> new UsernameNotFoundException("Username não encontrado"));

        return new UsuarioDetails(usuario);
    }
}
