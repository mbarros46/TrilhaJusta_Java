package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Curso;
import com.trilhajusta.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Curso> create(@RequestBody Curso c) { return ResponseEntity.status(201).body(service.save(c)); }

    @GetMapping("/{id}")
    public Curso get(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}")
    public Curso update(@PathVariable Long id, @RequestBody Curso c) { c.setId(id); return service.save(c); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
