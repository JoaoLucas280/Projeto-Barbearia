package com.JoaoLucas.Sistema.Barbearia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @Column
    private LocalTime horarioInicio;
    @Column
    private LocalTime horarioFim;

    @Column(name = "dia")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "barbeiro_dias_disponiveis", joinColumns = @JoinColumn(name = "barbeiro_id"))
    private Set<DayOfWeek> diasTrabalho = new HashSet<>();


}
