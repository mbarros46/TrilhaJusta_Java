package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Candidatura;
import com.trilhajusta.domain.enums.CandidaturaStatus;
import com.trilhajusta.dto.AtualizarStatusRequest;
import com.trilhajusta.services.CandidaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidaturas")
public class CandidaturaController {

    private final CandidaturaService service;

    public CandidaturaController(CandidaturaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Candidatura> criar(@RequestParam Long usuarioId, @RequestParam Long vagaId) {
        return ResponseEntity.status(201).body(service.criar(usuarioId, vagaId));
    }

    @PatchMapping("/{id}/status")
    public Candidatura atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest req) {
        return service.atualizarStatus(id, req.status());
    }

    @GetMapping
    public List<Candidatura> list() { return service.list(); }

    @GetMapping("/{id}")
    public Candidatura get(@PathVariable Long id) { return service.get(id); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
