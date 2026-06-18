package com.JoaoLucas.Sistema.Barbearia.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "agendamentos")
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @Getter
    @Setter
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id", nullable = false)
    @Getter
    @Setter
    private Barbeiro barbeiro;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    @Getter
    @Setter
    private Servico servico;

    @Column(nullable = false)
    @Getter
    @Setter
    private LocalDate data;

    @Column(nullable = false)
    @Getter
    @Setter
    private LocalTime horarioInicio;

    @Column(nullable = false)
    @Getter
    @Setter
    private LocalTime horarioFim;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCliente(), that.getCliente()) && Objects.equals(getBarbeiro(), that.getBarbeiro()) && Objects.equals(getServico(), that.getServico()) && Objects.equals(getData(), that.getData()) && Objects.equals(getHorarioInicio(), that.getHorarioInicio()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCliente(), getBarbeiro(), getServico(), getData(), getHorarioInicio(), getStatus());
    }
}
