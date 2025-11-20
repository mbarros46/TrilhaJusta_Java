package com.trilhajusta.controllers;

import com.trilhajusta.domain.entities.Competencia;
import com.trilhajusta.domain.entities.Usuario;
import com.trilhajusta.repositories.CompetenciaRepository;
import com.trilhajusta.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;
    private final CompetenciaRepository compRepo;

    public UsuarioController(UsuarioRepository repo, CompetenciaRepository compRepo) {
        this.repo = repo;
        this.compRepo = compRepo;
    }

    @GetMapping
    public Page<Usuario> list(Pageable pageable, @RequestParam(required = false) String competencia) {
        if (competencia != null) return repo.findByCompetencias_NomeIgnoreCase(competencia, pageable);
        return repo.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario u) {
        return ResponseEntity.status(201).body(repo.save(u));
    }

    @GetMapping("/{id}")
    public Usuario get(@PathVariable Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado")); }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id, @RequestBody @Valid Usuario u) { u.setId(id); return repo.save(u); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { repo.deleteById(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/{id}/competencias/{compId}")
    public Usuario addCompetencia(@PathVariable Long id, @PathVariable Long compId) {
        var u = repo.findById(id).orElseThrow();
        var c = compRepo.findById(compId).orElseThrow();
        u.getCompetencias().add(c);
        return repo.save(u);
    }

    @DeleteMapping("/{id}/competencias/{compId}")
    public Usuario removeCompetencia(@PathVariable Long id, @PathVariable Long compId) {
        var u = repo.findById(id).orElseThrow();
        var c = compRepo.findById(compId).orElseThrow();
        u.getCompetencias().remove(c);
        return repo.save(u);
    }
}
