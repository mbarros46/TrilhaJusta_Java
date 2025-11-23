package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Curso;
import com.trilhajusta.domain.entities.Trilha;
import com.trilhajusta.services.CursoService;
import com.trilhajusta.services.TrilhaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/trilhas")
public class TrilhaController {

    private final TrilhaService trilhaService;
    private final CursoService cursoService;

    public TrilhaController(TrilhaService trilhaService, CursoService cursoService) {
        this.trilhaService = trilhaService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public Page<Trilha> list(Pageable pageable) { return trilhaService.list(pageable); }

    @PostMapping
    public ResponseEntity<Trilha> create(@RequestBody @Valid Trilha t) {
        Trilha saved = trilhaService.save(t);
        return ResponseEntity.created(URI.create("/api/v1/trilhas/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public Trilha get(@PathVariable Long id) { return trilhaService.get(id); }

    @PutMapping("/{id}")
    public Trilha update(@PathVariable Long id, @RequestBody @Valid Trilha t) { t.setId(id); return trilhaService.save(t); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { trilhaService.delete(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/{id}/cursos")
    public List<Curso> cursos(@PathVariable Long id) { return cursoService.byTrilha(id); }
}
