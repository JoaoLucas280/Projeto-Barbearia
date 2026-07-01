package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.ServicoDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Servico;
import com.JoaoLucas.Sistema.Barbearia.exception.RecursoNaoEncontradoException;
import com.JoaoLucas.Sistema.Barbearia.mapper.Mapper;
import com.JoaoLucas.Sistema.Barbearia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ServicoService {

    private static final Logger log = LoggerFactory.getLogger(ServicoService.class);

    private final ServicoRepository repository;

    public ServicoDTO getServicoById(Long id) {
        log.info("Procurando serviço pelo id");
        var entity = repository.findById(id).orElseThrow
                (() -> new RecursoNaoEncontradoException("Serviço não encontrado"));
        return Mapper.map(entity, ServicoDTO.class);
    }

    public List<ServicoDTO> getAllServicos() {
        log.info("Procurando todos os serviços");
        return Mapper.mapListObjects(repository.findAll(), ServicoDTO.class);
    }

    public ServicoDTO saveServico(ServicoDTO servicoDTO) {
        log.info("Salvando serviço");
        var entity = Mapper.map(servicoDTO, Servico.class);
        var saved = repository.save(entity);
        var dto = Mapper.map(saved, ServicoDTO.class);
        dto.setId(saved.getId());
        return dto;
    }

    public ServicoDTO updateServico(ServicoDTO servico) {
        log.info("Atualizando serviço");
        Servico entity = repository.findById(servico.getId()).orElseThrow
                (() -> new RecursoNaoEncontradoException("Serviço não encontrado"));
        entity.setNome(servico.getNome());
        entity.setDescricao(servico.getDescricao());
        entity.setValor(servico.getValor());
        entity.setDuracao(servico.getDuracao());
        return Mapper.map(repository.save(entity), ServicoDTO.class);
    }

    public void deleteServico(Long id) {
        log.info("Deletando serviço");
        var entity =  repository.findById(id).orElseThrow
                (() -> new RecursoNaoEncontradoException("Serviço não encontrado"));
        repository.delete(entity);
    }
}
