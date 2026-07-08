package com.JoaoLucas.Sistema.Barbearia.entity;

import com.JoaoLucas.Sistema.Barbearia.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(getId(), usuario.getId()) && Objects.equals(getUsername(), usuario.getUsername()) && Objects.equals(getSenha(), usuario.getSenha()) && getRole() == usuario.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getSenha(), getRole());
    }
}
