package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.dto.ServicoDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Cliente;
import com.JoaoLucas.Sistema.Barbearia.entity.Servico;
import com.JoaoLucas.Sistema.Barbearia.mapper.ObjectMapper;
import com.JoaoLucas.Sistema.Barbearia.repository.ClienteRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                (() -> new IllegalArgumentException("Serviço não encontrado"));
        return ObjectMapper.map(entity, ServicoDTO.class);
    }

    public List<ServicoDTO> getAllServicos() {
        log.info("Procurando todos os serviços");
        return ObjectMapper.mapListObjects(repository.findAll(), ServicoDTO.class);
    }

    public ServicoDTO saveServico(ServicoDTO servicoDTO) {
        log.info("Salvando serviço");
        var entity = ObjectMapper.map(servicoDTO, Servico.class);
        var saved = repository.save(entity);
        var dto = ObjectMapper.map(saved, ServicoDTO.class);
        dto.setId(saved.getId());
        return dto;
    }

    public ServicoDTO updateServico(ServicoDTO servico) {
        log.info("Atualizando serviço");
        Servico entity = repository.findById(servico.getId()).orElseThrow
                (() -> new IllegalArgumentException("Serviço não encontrado"));
        entity.setNome(servico.getNome());
        entity.setDescricao(servico.getDescricao());
        entity.setValor(servico.getValor());
        entity.setDuracao(servico.getDuracao());
        return ObjectMapper.map(repository.save(entity), ServicoDTO.class);
    }

    public void deleteServico(Long id) {
        log.info("Deletando serviço");
        var entity =  repository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("Serviço não encontrado"));
        repository.delete(entity);
    }
}
