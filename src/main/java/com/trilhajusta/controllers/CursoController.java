package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Curso;
import com.trilhajusta.services.CursoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Curso> list(Pageable pageable) { return service.list(pageable); }

    @PostMapping
    public ResponseEntity<Curso> create(@RequestBody @Valid Curso c) {
        Curso saved = service.save(c);
        return ResponseEntity.created(URI.create("/api/v1/cursos/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public Curso get(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}")
    public Curso update(@PathVariable Long id, @RequestBody @Valid Curso c) { c.setId(id); return service.save(c); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
