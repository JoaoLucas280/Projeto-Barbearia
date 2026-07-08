package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.AgendamentoDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.Agendamento;
import com.JoaoLucas.Sistema.Barbearia.entity.Barbeiro;
import com.JoaoLucas.Sistema.Barbearia.entity.Cliente;
import com.JoaoLucas.Sistema.Barbearia.entity.Servico;
import com.JoaoLucas.Sistema.Barbearia.entity.enums.Status;
import com.JoaoLucas.Sistema.Barbearia.exception.ConflitoException;
import com.JoaoLucas.Sistema.Barbearia.exception.RecursoNaoEncontradoException;
import com.JoaoLucas.Sistema.Barbearia.repository.AgendamentoRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.BarbeiroRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ClienteRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ServicoRepository;
import com.JoaoLucas.Sistema.Barbearia.service.email_service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    BarbeiroRepository barbeiroRepository;
    @Mock
    ClienteRepository clienteRepository;
    @Mock
    ServicoRepository servicoRepository;
    @Mock
    AgendamentoRepository agendamentoRepository;
    @Mock
    EmailService emailService;

    @InjectMocks
    AgendamentoService agendamentoService;

@Test
    void deveCancelarAgendamentoComSucesso(){
        Cliente cliente = new Cliente();
        cliente.setEmail("joao@teste.com");
        Servico servico = new Servico();
        servico.setNome("Corte de Cabelo");

        Agendamento agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setStatus(Status.AGENDADO);
        agendamento.setData(LocalDate.now());
        agendamento.setHorarioInicio(LocalTime.of(10, 0));
        when(agendamentoRepository.findById(1L)).thenReturn(java.util.Optional.of(agendamento));

        agendamentoService.cancelarAgendamento(1L, "joao@teste.com");

        assertEquals(Status.CANCELADO, agendamento.getStatus());
        verify(agendamentoRepository).save(agendamento);
        verify(emailService).enviarEmail(any(), any(), any());
    }

    @Test
    void naoDeveCancelarAgendamentoComSucesso(){
        Cliente cliente = new Cliente();
        cliente.setEmail("joao@teste.com");
        Servico servico = new Servico();
        servico.setNome("Corte de Cabelo");

        Agendamento agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setStatus(Status.CANCELADO);
        agendamento.setData(LocalDate.now());
        agendamento.setHorarioInicio(LocalTime.of(10, 0));
        when(agendamentoRepository.findById(1L)).thenReturn(java.util.Optional.of(agendamento));

        assertThrows(ConflitoException.class, () -> agendamentoService.cancelarAgendamento(1L, "joao@teste.com"));
    }

    @Test
    void emailErrado(){
        Cliente cliente = new Cliente();
        cliente.setEmail("joao@teste.com");
        Servico servico = new Servico();
        servico.setNome("Corte de Cabelo");

        Agendamento agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setStatus(Status.AGENDADO);
        agendamento.setData(LocalDate.now());
        agendamento.setHorarioInicio(LocalTime.of(10, 0));
        when(agendamentoRepository.findById(1L)).thenReturn(java.util.Optional.of(agendamento));

        assertThrows(RecursoNaoEncontradoException.class, () -> agendamentoService.cancelarAgendamento(1L, "teste@teste.com"));
    }

    @Test
    void agendadoComSucesso(){
      Barbeiro barbeiro = new Barbeiro();
      barbeiro.setId(1L);
      barbeiro.setDiasTrabalho(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,  DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
      barbeiro.setHorarioInicio(LocalTime.of(10, 0));
      barbeiro.setHorarioFim(LocalTime.of(18, 0));

      Servico servico = new Servico();
      servico.setId(1L);
      servico.setNome("Corte de Cabelo");
      servico.setDuracao(30);

      Cliente cliente = new Cliente();
      cliente.setNome("João");
      cliente.setEmail("joao@teste.com");
      cliente.setTelefone("123456789");

      AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
      agendamentoDTO.setClienteNome(cliente.getNome());
      agendamentoDTO.setClienteEmail(cliente.getEmail());
      agendamentoDTO.setClienteTelefone(cliente.getTelefone());
      agendamentoDTO.setServicoId(servico.getId());
      agendamentoDTO.setData(LocalDate.of(2026, 7, 6));
      agendamentoDTO.setHorarioInicio(LocalTime.of(10, 0));

        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(barbeiro));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(clienteRepository.findByEmail("joao@teste.com")).thenReturn(Optional.of(cliente));
        when(agendamentoRepository.findByBarbeiroIdAndDataAndStatusNot(any(), any(), any())).thenReturn(List.of());

        Agendamento agendamentoSalvo = new Agendamento();
        agendamentoSalvo.setId(1L);
        agendamentoSalvo.setCliente(cliente);
        agendamentoSalvo.setServico(servico);
        agendamentoSalvo.setData(LocalDate.of(2026, 7, 6));
        agendamentoSalvo.setHorarioInicio(LocalTime.of(10, 0));
        agendamentoSalvo.setHorarioFim(LocalTime.of(10, 30));
        agendamentoSalvo.setStatus(Status.AGENDADO);
        when(agendamentoRepository.save(any())).thenReturn(agendamentoSalvo);

        agendamentoService.saveAgendamento(agendamentoDTO);

        verify(agendamentoRepository).save(any());
        verify(emailService).enviarEmail(any(), any(), any());
    }

    @Test
    void naoAgendadoDiaDaSemana(){
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setDiasTrabalho(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,  DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        barbeiro.setHorarioInicio(LocalTime.of(10, 0));
        barbeiro.setHorarioFim(LocalTime.of(18, 0));

        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte de Cabelo");
        servico.setDuracao(30);

        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@teste.com");
        cliente.setTelefone("123456789");

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        agendamentoDTO.setClienteNome(cliente.getNome());
        agendamentoDTO.setClienteEmail(cliente.getEmail());
        agendamentoDTO.setClienteTelefone(cliente.getTelefone());
        agendamentoDTO.setServicoId(servico.getId());
        agendamentoDTO.setData(LocalDate.of(2026, 7, 5));
        agendamentoDTO.setHorarioInicio(LocalTime.of(10, 0));

        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(barbeiro));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(clienteRepository.findByEmail("joao@teste.com")).thenReturn(Optional.of(cliente));


        assertThrows(IllegalArgumentException.class, () -> agendamentoService.saveAgendamento(agendamentoDTO));

    }

    @Test
    void falhaForaDoExpediente() {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setDiasTrabalho(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        barbeiro.setHorarioInicio(LocalTime.of(10, 0));
        barbeiro.setHorarioFim(LocalTime.of(18, 0));

        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte de Cabelo");
        servico.setDuracao(30);

        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@teste.com");
        cliente.setTelefone("123456789");

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        agendamentoDTO.setClienteNome(cliente.getNome());
        agendamentoDTO.setClienteEmail(cliente.getEmail());
        agendamentoDTO.setClienteTelefone(cliente.getTelefone());
        agendamentoDTO.setServicoId(servico.getId());
        agendamentoDTO.setData(LocalDate.of(2026, 7, 7));
        agendamentoDTO.setHorarioInicio(LocalTime.of(9, 0));

        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(barbeiro));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(clienteRepository.findByEmail("joao@teste.com")).thenReturn(Optional.of(cliente));


        assertThrows(IllegalArgumentException.class, () -> agendamentoService.saveAgendamento(agendamentoDTO));
    }

    @Test
    void naoAgendadoConflitoHorário() {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setDiasTrabalho(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        barbeiro.setHorarioInicio(LocalTime.of(10, 0));
        barbeiro.setHorarioFim(LocalTime.of(18, 0));

        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte de Cabelo");
        servico.setDuracao(30);

        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@teste.com");
        cliente.setTelefone("123456789");

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        agendamentoDTO.setClienteNome(cliente.getNome());
        agendamentoDTO.setClienteEmail(cliente.getEmail());
        agendamentoDTO.setClienteTelefone(cliente.getTelefone());
        agendamentoDTO.setServicoId(servico.getId());
        agendamentoDTO.setData(LocalDate.of(2026, 7, 6));
        agendamentoDTO.setHorarioInicio(LocalTime.of(10, 0));

        Agendamento agendamentoSalvo = new Agendamento();
        agendamentoSalvo.setId(1L);
        agendamentoSalvo.setCliente(cliente);
        agendamentoSalvo.setServico(servico);
        agendamentoSalvo.setData(LocalDate.of(2026, 7, 6));
        agendamentoSalvo.setHorarioInicio(LocalTime.of(10, 0));
        agendamentoSalvo.setHorarioFim(LocalTime.of(10, 30));
        agendamentoSalvo.setStatus(Status.AGENDADO);
        when(agendamentoRepository.findByBarbeiroIdAndDataAndStatusNot(any(), any(), any())).thenReturn(List.of(agendamentoSalvo));


        when(barbeiroRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(barbeiro));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(clienteRepository.findByEmail("joao@teste.com")).thenReturn(Optional.of(cliente));


        assertThrows(ConflitoException.class, () -> agendamentoService.saveAgendamento(agendamentoDTO));
    }
}
