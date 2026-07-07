package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.AgendamentoDTO;
import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Cliente;
import com.JoaoLucas.Sistema.Barbearia.exception.RecursoNaoEncontradoException;
import com.JoaoLucas.Sistema.Barbearia.repository.AgendamentoRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;
    @Mock
    AgendamentoRepository agendamentoRepository;
    @Mock
    AgendamentoService agendamentoService;

    @InjectMocks
    ClienteService clienteService;

    @Test
    void buscaPorIdSucesso(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("John Doe");
        clienteDTO.setEmail("john.doe@example.com");
        clienteDTO.setTelefone("1234567890");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

       assertEquals(clienteDTO, clienteService.getClienteById(1L));
       verify(clienteRepository).findById(1L);
    }

    @Test
    void buscaPorIdNaoEncontrado(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");


        assertThrows(RecursoNaoEncontradoException.class, () -> {
            clienteService.getClienteById(2L);
        });
    }

    @Test
    void buscaPorEmailSucesso(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("John Doe");
        clienteDTO.setEmail("john.doe@example.com");
        clienteDTO.setTelefone("1234567890");

        when(clienteRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(cliente));

        assertEquals(clienteDTO, clienteService.getClienteByEmail("john.doe@example.com"));
        verify(clienteRepository).findByEmail("john.doe@example.com");
    }

    @Test
    void buscaPorEmailNaoEncontrado(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");


        assertThrows(RecursoNaoEncontradoException.class, () -> {
            clienteService.getClienteByEmail("jane.doe@example.com");
        });
    }

    @Test
    void updateClienteSucesso(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setEmail("jon.doe@example.com");
        clienteDTO.setTelefone(cliente.getTelefone());

        cliente.setEmail(clienteDTO.getEmail());

        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        assertEquals(clienteDTO, clienteService.updateCliente(clienteDTO));
    }

    @Test
    void updateClienteNaoEncontrado(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");


        assertThrows(RecursoNaoEncontradoException.class, () -> {
            clienteService.getClienteById(2L);
        });
    }
    @Test
    void deleteClienteSucesso(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(agendamentoService.temAgendamentosAtivos(1L)).thenReturn(false);

        clienteService.deleteCliente(1L);

        verify(agendamentoRepository).deleteByClienteId(1L);
        verify(clienteRepository).delete(cliente);
    }

    @Test
    void deleteClienteNaoEncontrado(){
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clienteService.deleteCliente(1L));
        verify(clienteRepository, never()).delete(any());
    }

    @Test
    void deleteClienteComAgendamentosAtivos(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(agendamentoService.temAgendamentosAtivos(1L)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> clienteService.deleteCliente(1L));
        verify(clienteRepository, never()).delete(any());
    }


}
