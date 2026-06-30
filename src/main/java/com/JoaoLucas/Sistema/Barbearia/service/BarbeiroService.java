package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.BarbeiroDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Barbeiro;
import com.JoaoLucas.Sistema.Barbearia.exception.ConflitoException;
import com.JoaoLucas.Sistema.Barbearia.exception.RecursoNaoEncontradoException;
import com.JoaoLucas.Sistema.Barbearia.mapper.Mapper;
import com.JoaoLucas.Sistema.Barbearia.repository.BarbeiroRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BarbeiroService {

    private static final Logger log = LoggerFactory.getLogger(BarbeiroService.class);


    private final BarbeiroRepository barbeiroRepository;

   public BarbeiroDTO getBarbeiro() {
        log.info("Procurando barbeiro pelo id");
        var entity  = barbeiroRepository.findFirstByOrderByIdAsc().orElseThrow
                (() -> new RecursoNaoEncontradoException("Barbeiro não encontrado"));
        return Mapper.map(entity, BarbeiroDTO.class);
   }

    public BarbeiroDTO saveBarbeiro(BarbeiroDTO barbeiroDTO) {
        log.info("Salvando barbeiro");
        if (barbeiroRepository.count() > 0){
            throw new ConflitoException("Barbeiro já existente");
        }
        var entity = Mapper.map(barbeiroDTO, Barbeiro.class);
        var saved = barbeiroRepository.save(entity);
        var dto = Mapper.map(saved, BarbeiroDTO.class);
        dto.setId(saved.getId());
        return dto;
    }

    public BarbeiroDTO updateBarbeiro(BarbeiroDTO barbeiro) {
        log.info("Atualizando barbeiro");
        Barbeiro entity = barbeiroRepository.findFirstByOrderByIdAsc().orElseThrow
                (() -> new RecursoNaoEncontradoException("Barbeiro não encontrado"));
        entity.setNome(barbeiro.getNome());
        entity.setDiasTrabalho(barbeiro.getDiasTrabalho());
        entity.setEspecialidade(barbeiro.getEspecialidade());
        entity.setDescricao(barbeiro.getDescricao());
        entity.setHorarioInicio(barbeiro.getHorarioInicio());
        entity.setHorarioFim(barbeiro.getHorarioFim());
        return Mapper.map(barbeiroRepository.save(entity), BarbeiroDTO.class);
    }

}
