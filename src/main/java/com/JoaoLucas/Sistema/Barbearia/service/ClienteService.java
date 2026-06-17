package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Cliente;
import com.JoaoLucas.Sistema.Barbearia.mapper.ObjectMapper;
import com.JoaoLucas.Sistema.Barbearia.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClienteService {

    private static  Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDTO getClienteById(Long id) {
        log.info("Procurando cliente pelo id");
        var entity  = clienteRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("Cliente não encontrado"));
        return ObjectMapper.map(entity, ClienteDTO.class);
    }

    public List<ClienteDTO> getAllClients() {
        log.info("Procurando todos os clientes");
        return ObjectMapper.mapListObjects(clienteRepository.findAll(), ClienteDTO.class);
    }

    public ClienteDTO getClienteByEmail(String email) {
        log.info("Procurando cliente pelo email");
        var entity =  clienteRepository.findByEmail(email).orElseThrow
                (() -> new IllegalArgumentException("Cliente não encontrado com este email"));
        return ObjectMapper.map(entity, ClienteDTO.class);
    }

    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        log.info("Salvando cliente");
        var entity = ObjectMapper.map(clienteDTO, Cliente.class);
        var saved = clienteRepository.save(entity);
        var dto = ObjectMapper.map(saved, ClienteDTO.class);
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
        return ObjectMapper.map(clienteRepository.save(entity), ClienteDTO.class);
    }

    public void deleteCliente(Long id) {
        log.info("Deletando cliente");
        var entity =  clienteRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("Cliente não encontrado"));
        clienteRepository.delete(entity);
    }
}
