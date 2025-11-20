package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Vaga;
import com.trilhajusta.services.VagaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vagas")
public class VagaController {

    private final VagaService service;

    public VagaController(VagaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Vaga> list(Pageable pageable,
                           @RequestParam(required = false) String cidade,
                           @RequestParam(required = false) String uf,
                           @RequestParam(required = false) String competencia) {
        if (cidade != null && uf != null) return service.byLocal(cidade, uf, pageable);
        if (competencia != null) return service.byCompetencia(competencia, pageable);
        return service.list(pageable);
    }

    @PostMapping
    public ResponseEntity<Vaga> create(@RequestBody Vaga v) { return ResponseEntity.status(201).body(service.save(v)); }

    @GetMapping("/{id}")
    public Vaga get(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}")
    public Vaga update(@PathVariable Long id, @RequestBody Vaga v) { v.setId(id); return service.save(v); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
