package com.JoaoLucas.Sistema.Barbearia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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

}
