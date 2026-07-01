package com.JoaoLucas.Sistema.Barbearia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "barbeiros")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @Column
    private LocalTime horarioInicio;
    @Column
    private LocalTime horarioFim;

    @Column(name = "dia")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "barbeiro_dias_disponiveis", joinColumns = @JoinColumn(name = "barbeiro_id"))
    private Set<DayOfWeek> diasTrabalho = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Barbeiro barbeiro)) return false;
        return Objects.equals(getId(), barbeiro.getId()) && Objects.equals(getNome(), barbeiro.getNome()) && Objects.equals(getEspecialidade(), barbeiro.getEspecialidade()) && Objects.equals(getDescricao(), barbeiro.getDescricao()) && Objects.equals(getHorarioInicio(), barbeiro.getHorarioInicio()) && Objects.equals(getHorarioFim(), barbeiro.getHorarioFim()) && Objects.equals(getDiasTrabalho(), barbeiro.getDiasTrabalho());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEspecialidade(), getDescricao(), getHorarioInicio(), getHorarioFim(), getDiasTrabalho());
    }
}
