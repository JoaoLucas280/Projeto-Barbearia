package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.BarbeiroDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Barbeiro;
import com.JoaoLucas.Sistema.Barbearia.exception.ConflitoException;
import com.JoaoLucas.Sistema.Barbearia.exception.RecursoNaoEncontradoException;
import com.JoaoLucas.Sistema.Barbearia.repository.BarbeiroRepository;
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
public class BarbeiroServiceTest {

    @Mock
    BarbeiroRepository barbeiroRepository;

    @InjectMocks
    BarbeiroService barbeiroService;

    @Test
    void getBarbeiroSucesso() {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setNome("João");

        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(barbeiro));

        BarbeiroDTO resultado = barbeiroService.getBarbeiro();

        assertEquals("João", resultado.getNome());
        verify(barbeiroRepository).findFirstByOrderByIdAsc();
    }

    @Test
    void getBarbeiroNaoEncontrado() {
        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> barbeiroService.getBarbeiro());
    }

    @Test
    void saveBarbeiroSucesso() {
        BarbeiroDTO barbeiroDTO = new BarbeiroDTO();
        barbeiroDTO.setNome("João");

        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setNome("João");

        when(barbeiroRepository.count()).thenReturn(0L);
        when(barbeiroRepository.save(any())).thenReturn(barbeiro);

        BarbeiroDTO resultado = barbeiroService.saveBarbeiro(barbeiroDTO);

        assertEquals("João", resultado.getNome());
        verify(barbeiroRepository).save(any());
    }

    @Test
    void saveBarbeiroJaExistente() {
        when(barbeiroRepository.count()).thenReturn(1L);

        assertThrows(ConflitoException.class, () -> barbeiroService.saveBarbeiro(new BarbeiroDTO()));
        verify(barbeiroRepository, never()).save(any());
    }

    @Test
    void updateBarbeiroSucesso() {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setNome("João");

        BarbeiroDTO barbeiroDTO = new BarbeiroDTO();
        barbeiroDTO.setNome("João Atualizado");

        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(barbeiro));
        when(barbeiroRepository.save(any())).thenReturn(barbeiro);

        barbeiroService.updateBarbeiro(barbeiroDTO);

        verify(barbeiroRepository).save(any());
    }

    @Test
    void updateBarbeiroNaoEncontrado() {
        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> barbeiroService.updateBarbeiro(new BarbeiroDTO()));
        verify(barbeiroRepository, never()).save(any());
    }
}
