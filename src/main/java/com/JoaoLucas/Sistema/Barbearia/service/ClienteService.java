package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Cliente;
import com.JoaoLucas.Sistema.Barbearia.exception.RecursoNaoEncontradoException;
import com.JoaoLucas.Sistema.Barbearia.mapper.Mapper;
import com.JoaoLucas.Sistema.Barbearia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    public ClienteDTO getClienteById(Long id) {
        log.info("Procurando cliente pelo id");
        var entity  = clienteRepository.findById(id).orElseThrow
                (() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        return Mapper.map(entity, ClienteDTO.class);
    }

    public List<ClienteDTO> getAllClients() {
        log.info("Procurando todos os clientes");
        return Mapper.mapListObjects(clienteRepository.findAll(), ClienteDTO.class);
    }

    public ClienteDTO getClienteByEmail(String email) {
        log.info("Procurando cliente pelo email");
        var entity =  clienteRepository.findByEmail(email).orElseThrow
                (() -> new RecursoNaoEncontradoException("Cliente não encontrado com este email"));
        return Mapper.map(entity, ClienteDTO.class);
    }

    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        log.info("Salvando cliente");
        var entity = Mapper.map(clienteDTO, Cliente.class);
        var saved = clienteRepository.save(entity);
        var dto = Mapper.map(saved, ClienteDTO.class);
        dto.setId(saved.getId());
        return dto;
    }

    public ClienteDTO updateCliente(ClienteDTO cliente) {
        log.info("Atualizando cliente");
        Cliente entity = clienteRepository.findById(cliente.getId()).orElseThrow
                (() -> new IllegalArgumentException("Cliente não encontrado"));
        entity.setNome(cliente.getNome());
        entity.setEmail(cliente.getEmail());
        entity.setTelefone(cliente.getTelefone());
        return Mapper.map(clienteRepository.save(entity), ClienteDTO.class);
    }

    public void deleteCliente(Long id) {
        log.info("Deletando cliente");
        var entity =  clienteRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("Cliente não encontrado"));
        clienteRepository.delete(entity);
    }
}
