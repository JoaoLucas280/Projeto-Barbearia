package com.JoaoLucas.Sistema.Barbearia.controller;

import com.JoaoLucas.Sistema.Barbearia.dto.BarbeiroDTO;
import com.JoaoLucas.Sistema.Barbearia.dto.ClienteDTO;
import com.JoaoLucas.Sistema.Barbearia.service.BarbeiroService;
import com.JoaoLucas.Sistema.Barbearia.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/barbeiro/v1")
@RequiredArgsConstructor
public class BarbeiroController {


    private final BarbeiroService service;

    @GetMapping
    public ResponseEntity<BarbeiroDTO> buscaBarbeiro() {
        return ResponseEntity.ok(service.getBarbeiro());
    }

    @PostMapping
    public ResponseEntity<BarbeiroDTO> criaBarbeiro(@Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        BarbeiroDTO created = service.saveBarbeiro(barbeiroDTO);
        URI location = URI.create(String.format("/api/barbeiro/v1/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping
    public ResponseEntity<BarbeiroDTO> atualizaBarbeiro(@Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        BarbeiroDTO updated = service.updateBarbeiro(barbeiroDTO);
        return ResponseEntity.ok(updated);
    }
}
