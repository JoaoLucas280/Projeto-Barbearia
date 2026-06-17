package com.JoaoLucas.Sistema.Barbearia.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "servicos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column
    private String descricao;
    @Column(nullable = false)
    private Integer duracao;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return Objects.equals(getId(), servico.getId()) && Objects.equals(getNome(), servico.getNome()) && Objects.equals(getValor(), servico.getValor()) && Objects.equals(getDescricao(), servico.getDescricao()) && Objects.equals(getDuracao(), servico.getDuracao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getValor(), getDescricao(), getDuracao());
    }
}
