package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.AgendamentoDTO;
import com.JoaoLucas.Sistema.Barbearia.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Tag(name = "Agendamentos", description = "Operações de agendamento de horários")
@RestController
@RequestMapping("/api/agendamentos/v1")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @Operation(summary = "Cria um novo Agendamento",
            description = "Se o cliente não existir (buscado por email), ele é criado automaticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Horário fora do expediente ou dia não disponível"),
            @ApiResponse(responseCode = "404", description = "Serviço ou barbeiro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito com um agendamento já existente")
    })
    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recebe um DTO para ser modelado e transformado em Agendamento") @Valid @RequestBody AgendamentoDTO agendamento) {
        AgendamentoDTO created = service.saveAgendamento(agendamento);
        URI location = URI.create(String.format("/api/agendamentos/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Retorna os horários disponíveis",
        description = "Requisição pública onde a pessoa consegue visualizar os horários disponíveis do barbeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna horários disponíveis"),
            @ApiResponse(responseCode = "400", description = "Data Inválida"),
            @ApiResponse(responseCode = "404", description = "ServicoId não existe")
    })
    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<LocalTime>> listarHorariosDisponiveis(
            @Parameter(description = "Usa data pra verificar os horários nessa data específica") @RequestParam LocalDate data,
            @Parameter(description = "Utiliza ID de servico para calcular a duração e se é válido listar o horário na lista")@RequestParam Long servicoid) {
        List<LocalTime> horarios = service.listarHorariosDisponiveis(data, servicoid);
        return ResponseEntity.ok(horarios);
    }

    @Operation(summary = "Lista Agendamentos por email",
            description = "Requisição que retorna o histórico de agendamentos recebendo email de parâmetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna lista de agendamentos de acordo com email"),
            @ApiResponse(responseCode = "204", description = "Email Não digitado"),
            @ApiResponse(responseCode = "404", description = "Email Não existe")
    })
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamentosPorEmail(
            @Parameter(description = "Recebe um email que deve ter a configuração correta com @ e .com") @RequestParam(value = "email", required = false) String email) {
        if (email != null && !email.isBlank()) {
            return ResponseEntity.ok(service.buscarPorEmailCliente(email));
        }
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Lista todos os agendamentos",
            description = "Retorna uma lista de todos os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de todos os agendamentos"),
            @ApiResponse(responseCode = "403", description = "Sem permissão pra acessar esse método")
    })
    @GetMapping("/todos")
    public ResponseEntity<List<AgendamentoDTO>> listarTodos(){
        var buscados = service.buscarTodosAgendamentos();
        return ResponseEntity.ok(buscados);
    }

    @Operation(summary = "Cancela um agendamento",
            description = "Cancela um agendamento somente se estiver agendado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento cancelado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Agendamento não tem status AGENDADO havendo conflito"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado ou não existe para o email")
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAgendamento(
            @Parameter(description = "Busca agendamento por id")@PathVariable Long id,
            @Parameter(description = "Usado pra identificar de quem é o agendamento")@RequestParam String email) {
        service.cancelarAgendamento(id, email);
        return ResponseEntity.noContent().build();
    }
}
