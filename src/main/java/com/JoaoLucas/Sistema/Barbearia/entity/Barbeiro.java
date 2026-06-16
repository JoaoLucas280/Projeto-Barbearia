package com.JoaoLucas.Sistema.Barbearia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "barbeiros")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String especialidade;
    @Column
    private String descricao;

    @Column(nullable = false)
    private Double avaliacao;
    @Column
    private LocalTime horario_inicio;
    @Column
    private LocalTime horario_fim;

    public void setHorario_inicio() {
        horario_inicio = LocalTime.now();
        horario_fim = horario_inicio.plusHours(1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Barbeiro barbeiro = (Barbeiro) o;
        return Objects.equals(getId(), barbeiro.getId()) && Objects.equals(getNome(), barbeiro.getNome()) && Objects.equals(getEspecialidade(), barbeiro.getEspecialidade()) && Objects.equals(getDescricao(), barbeiro.getDescricao()) && Objects.equals(getAvaliacao(), barbeiro.getAvaliacao()) && Objects.equals(getHorario_inicio(), barbeiro.getHorario_inicio()) && Objects.equals(getHorario_fim(), barbeiro.getHorario_fim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEspecialidade(), getDescricao(), getAvaliacao(), getHorario_inicio(), getHorario_fim());
    }
}
