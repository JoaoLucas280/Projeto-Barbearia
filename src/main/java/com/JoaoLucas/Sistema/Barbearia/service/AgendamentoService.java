package com.JoaoLucas.Sistema.Barbearia.service;

import com.JoaoLucas.Sistema.Barbearia.dto.AgendamentoDTO;
import com.JoaoLucas.Sistema.Barbearia.entity.*;
import com.JoaoLucas.Sistema.Barbearia.mapper.ObjectMapper;
import com.JoaoLucas.Sistema.Barbearia.repository.AgendamentoRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.BarbeiroRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ClienteRepository;
import com.JoaoLucas.Sistema.Barbearia.repository.ServicoRepository;
import com.JoaoLucas.Sistema.Barbearia.service.email_service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
            if (haConflito(inicio, fim, agendamento)){
                throw new IllegalArgumentException("Conflito de horário com outro agendamento");
            }
        }
    }

    @Transactional
    public AgendamentoDTO saveAgendamento(AgendamentoDTO dto) {

        var cliente = buscarPorEmail(dto);
        var barbeiro = buscarBarbeiro();
        var servico = buscarServico(dto.getServicoId());
        var horarioFim = horarioFim(servico, dto);
        validarDiaAtendimento(barbeiro, dto.getData(), dto);
        validarDentroExpediente(barbeiro, dto.getHorarioInicio(), horarioFim);
        validarConflito(barbeiro.getId(), dto.getData(), dto.getHorarioInicio(), horarioFim);

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServico(servico);
        agendamento.setData(dto.getData());
        agendamento.setHorarioInicio(dto.getHorarioInicio());
        agendamento.setHorarioFim(horarioFim);
        agendamento.setStatus(Status.AGENDADO);

        var saved = agendamentoRepository.save(agendamento);

        emailService.enviarEmail(cliente.getEmail(), "Agendamento confirmado", "Seu agendamento para o serviço " + servico.getNome() + " no dia " + dto.getData() + " às " + dto.getHorarioInicio() + " foi confirmado.");

        return ObjectMapper.map(saved, AgendamentoDTO.class);

    }

    private boolean haConflito(LocalTime inicio, LocalTime fim, Agendamento existente) {
        return inicio.isBefore(existente.getHorarioFim()) && fim.isAfter(existente.getHorarioInicio());
    }

    public List<LocalTime> listarHorariosDisponiveis(LocalDate data, Long servicoId){
        Barbeiro barbeiro = buscarBarbeiro();
        Servico servico = buscarServico(servicoId);
        List<LocalTime> horariosDisponiveis = new ArrayList<>();
        LocalTime horario_candidato = barbeiro.getHorarioInicio();
        List<Agendamento> listaDeAgendamentos = agendamentoRepository.findByBarbeiroIdAndDataAndStatusNot(barbeiro.getId(), data, Status.CANCELADO);

        if (!barbeiro.getDiasTrabalho().contains(data.getDayOfWeek())) return List.of();

        while (!horario_candidato.plusMinutes(servico.getDuracao()).isAfter(barbeiro.getHorarioFim()) || horario_candidato.plusMinutes(servico.getDuracao()).equals(barbeiro.getHorarioFim())) {
           LocalTime final_candidato = horario_candidato.plusMinutes(servico.getDuracao());
            boolean conflitante = false;

           for (Agendamento agendamento : listaDeAgendamentos) {
               if (haConflito(horario_candidato, final_candidato, agendamento))  conflitante = true;
           }
           if  (!conflitante) {
               horariosDisponiveis.add(horario_candidato);
           }
           horario_candidato = horario_candidato.plusMinutes(servico.getDuracao());
        }
        return horariosDisponiveis;
    }

    public List<AgendamentoDTO> buscarPorEmailCliente(String email) {
        log.info("Buscando agendamentos pelo email do cliente");
        var agendamentos = agendamentoRepository.findByClienteEmail(email);
        return ObjectMapper.mapListObjects(agendamentos, AgendamentoDTO.class);
    }

     public void cancelarAgendamento(Long id, String email) {
        log.info("Cancelando agendamento");
        var entity = agendamentoRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("Agendamento não encontrado"));
        if (!entity.getStatus().equals(Status.AGENDADO)) throw new IllegalArgumentException("Agendamento não pode ser cancelado pois já está com status: " + entity.getStatus());
        var emailCorresponde = entity.getCliente().getEmail().equals(email);
        if (!emailCorresponde) throw new IllegalArgumentException("Agendamento não encontrado para este email");
        entity.setStatus(Status.CANCELADO);
        agendamentoRepository.save(entity);

        emailService.enviarEmail(entity.getCliente().getEmail(), "Agendamento cancelado", "Seu agendamento para o serviço " + entity.getServico().getNome() + " no dia " + entity.getData() + " às " + entity.getHorarioInicio() + " foi cancelado.");
    }

}
