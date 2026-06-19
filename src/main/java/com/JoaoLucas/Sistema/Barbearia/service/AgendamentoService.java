package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.AgendamentoDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.*;
import com.JoaoLucas.Sistema.Barbearia.repository.AgendamentoRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.BarbeiroRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ClienteRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ServicoRepository;
import com.JoaoLucas.Sistema.Barbearia.service.email_service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final BarbeiroRepository barbeiroRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final EmailService emailService;

    public Cliente buscarPorEmail(AgendamentoDTO dto) {
        var entity =  clienteRepository.findByEmail(dto.getClienteEmail());

        if (entity.isEmpty()){
            Cliente cliente = new  Cliente();
            cliente.setEmail(dto.getClienteEmail());
            cliente.setNome(dto.getClienteNome());
            cliente.setTelefone(dto.getClienteTelefone());
            clienteRepository.save(cliente);
            return cliente;
        }
        return entity.orElseThrow(() -> new IllegalStateException("Cliente não encontrado com este email"));
    }

    public Barbeiro buscarBarbeiro (){
        var entity = barbeiroRepository.findFirstByOrderByIdAsc().orElseThrow
                (() -> new IllegalStateException("Barbeiro não encontrado"));
        return entity;
    }

    public Servico buscarServico (Long id){
        var entity = servicoRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("Serviço não encontrado"));
        return entity;
    }

    public LocalTime horarioFim (Servico servico, AgendamentoDTO dto) {
        return dto.getHorarioInicio().plusMinutes(servico.getDuracao());
    }

    private void validarDiaAtendimento(Barbeiro barbeiro, LocalDate data, AgendamentoDTO dto) {
        var entity = barbeiro.getDiasTrabalho().contains(dto.getData().getDayOfWeek());
        if (!entity){
            throw new IllegalArgumentException("Barbeiro não atende neste dia");
        }
    }

    private void validarDentroExpediente(Barbeiro barbeiro, LocalTime inicio, LocalTime fim) {
        if (barbeiro.getHorarioInicio().isAfter(inicio) || barbeiro.getHorarioFim().isBefore(fim)){
            throw new IllegalArgumentException("Agendamento fora do horário de expediente do barbeiro");
        }
    }

    private void validarConflito(Long barbeiroId, LocalDate data, LocalTime inicio, LocalTime fim) {
        List<Agendamento> entity =
                agendamentoRepository.findByBarbeiroIdAndDataAndStatusNot(barbeiroId, data, Status.CANCELADO);

        for (int i = 0 ;  i < entity.size() ; i++){
            var agendamento = entity.get(i);
            if (inicio.isBefore(agendamento.getHorarioFim()) && fim.isAfter(agendamento.getHorarioInicio())){
                throw new IllegalArgumentException("Conflito de horário com outro agendamento");
            }
        }
    }

}
