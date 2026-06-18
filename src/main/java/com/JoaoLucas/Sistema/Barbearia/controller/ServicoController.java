package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.dto.ServicoDTO;
import com.JoaoLucas.Sistema.Barbearia.service.ClienteService;
import com.JoaoLucas.Sistema.Barbearia.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/servicos/v1")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService service;


    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscaServicoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getServicoById(id));
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> buscaTodosServicos() {
        return ResponseEntity.ok(service.getAllServicos());
    }

    @PostMapping
    public ResponseEntity<ServicoDTO> criaServico(@Valid @RequestBody ServicoDTO servicoDTO) {
        ServicoDTO created = service.saveServico(servicoDTO);
        URI location = URI.create(String.format("/api/servicos/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizaServico(@PathVariable Long id, @Valid @RequestBody ServicoDTO servicoDTO) {
        servicoDTO.setId(id);
        ServicoDTO updated = service.updateServico(servicoDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        service.deleteServico(id);
        return ResponseEntity.noContent().build();
    }
}
