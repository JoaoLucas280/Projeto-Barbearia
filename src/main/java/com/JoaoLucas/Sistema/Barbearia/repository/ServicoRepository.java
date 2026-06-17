package com.JoaoLucas.Sistema.Barbearia.repository;

import com.JoaoLucas.Sistema.Barbearia.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}

