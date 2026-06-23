package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.AgendamentoDTO;
import com.JoaoLucas.Sistema.Barbearia.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos/v1")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@Valid @RequestBody AgendamentoDTO agendamento) {
        AgendamentoDTO created = service.saveAgendamento(agendamento);
        URI location = URI.create(String.format("/api/agendamentos/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<LocalTime>> listarHorariosDisponiveis(@RequestParam LocalDate data, @RequestParam Long servicoid) {
        List<LocalTime> horarios = service.listarHorariosDisponiveis(data, servicoid);
        return ResponseEntity.ok(horarios);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamentosPorEmail(@RequestParam(value = "email", required = false) String email) {
        if (email != null && !email.isBlank()) {
            return ResponseEntity.ok(service.buscarPorEmailCliente(email));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<AgendamentoDTO>> listarTodos(){
        var buscados = service.buscarTodosAgendamentos();
        return ResponseEntity.ok(buscados);
    }


    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable Long id, @RequestParam String email) {
        service.cancelarAgendamento(id, email);
        return ResponseEntity.noContent().build();
    }
}
