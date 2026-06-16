package com.JoaoLucas.Sistema.Barbearia.repository;

import com.JoaoLucas.Sistema.Barbearia.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByBarbeiroIdAndData(Long barbeiroId, LocalDate data);
}

