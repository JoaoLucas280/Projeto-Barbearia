package com.JoaoLucas.Sistema.Barbearia.service;


import com.JoaoLucas.Sistema.Barbearia.dto.ServicoDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Servico;
import com.JoaoLucas.Sistema.Barbearia.exception.RecursoNaoEncontradoException;
import com.JoaoLucas.Sistema.Barbearia.repository.ServicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicoServiceTest {

    @Mock
    ServicoRepository repository;

    @InjectMocks
    ServicoService servicoService;

    @Test
    void getServicoByIdSucesso() {
        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte");

        when(repository.findById(1L)).thenReturn(Optional.of(servico));

        ServicoDTO resultado = servicoService.getServicoById(1L);

        assertEquals("Corte", resultado.getNome());
        verify(repository).findById(1L);
    }

    @Test
    void getServicoByIdNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> servicoService.getServicoById(1L));
    }

    @Test
    void saveServicoSucesso() {
        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setNome("Corte");

        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte");

        when(repository.save(any())).thenReturn(servico);

        ServicoDTO resultado = servicoService.saveServico(servicoDTO);

        assertEquals("Corte", resultado.getNome());
        verify(repository).save(any());
    }

    @Test
    void updateServicoSucesso() {
        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte");

        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setId(1L);
        servicoDTO.setNome("Corte Atualizado");

        when(repository.findById(1L)).thenReturn(Optional.of(servico));
        when(repository.save(any())).thenReturn(servico);

        servicoService.updateServico(servicoDTO);

        verify(repository).save(any());
    }

    @Test
    void updateServicoNaoEncontrado() {
        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> servicoService.updateServico(servicoDTO));
        verify(repository, never()).save(any());
    }

    @Test
    void deleteServicoSucesso() {
        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte");

        when(repository.findById(1L)).thenReturn(Optional.of(servico));

        servicoService.deleteServico(1L);

        verify(repository).delete(servico);
    }

    @Test
    void deleteServicoNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> servicoService.deleteServico(1L));
        verify(repository, never()).delete(any());
    }
}
