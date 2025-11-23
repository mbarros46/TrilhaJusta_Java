package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Candidatura;
import com.trilhajusta.domain.enums.CandidaturaStatus;
import com.trilhajusta.dto.AtualizarStatusRequest;
import com.trilhajusta.services.CandidaturaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/candidaturas")
public class CandidaturaController {

    private final CandidaturaService service;

    public CandidaturaController(CandidaturaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Candidatura> criar(@RequestParam Long usuarioId, @RequestParam Long vagaId) {
        Candidatura saved = service.criar(usuarioId, vagaId);
        return ResponseEntity.created(URI.create("/api/v1/candidaturas/" + saved.getId())).body(saved);
    }

    @PatchMapping("/{id}/status")
    public Candidatura atualizarStatus(@PathVariable Long id, @RequestBody @Valid AtualizarStatusRequest req) {
        return service.atualizarStatus(id, req.status());
    }

    @GetMapping
    public Page<Candidatura> list(Pageable pageable) { return service.list(pageable); }

    @GetMapping("/{id}")
    public Candidatura get(@PathVariable Long id) { return service.get(id); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
