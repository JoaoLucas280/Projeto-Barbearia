package com.JoaoLucas.Sistema.Barbearia.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "agendamentos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private LocalTime horario_inicio;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCliente(), that.getCliente()) && Objects.equals(getBarbeiro(), that.getBarbeiro()) && Objects.equals(getServico(), that.getServico()) && Objects.equals(getData(), that.getData()) && Objects.equals(getHorario_inicio(), that.getHorario_inicio()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCliente(), getBarbeiro(), getServico(), getData(), getHorario_inicio(), getStatus());
    }
}
