package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Competencia;
import com.trilhajusta.services.CompetenciaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/competencias")
public class CompetenciaController {

    private final CompetenciaService service;

    public CompetenciaController(CompetenciaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Competencia> list(Pageable pageable) { return service.list(pageable); }

    @PostMapping
    public ResponseEntity<Competencia> create(@RequestBody Competencia c) {
        return ResponseEntity.status(201).body(service.save(c));
    }

    @GetMapping("/{id}")
    public Competencia get(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}")
    public Competencia update(@PathVariable Long id, @RequestBody Competencia c) {
        c.setId(id);
        return service.save(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
