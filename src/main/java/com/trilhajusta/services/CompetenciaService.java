package com.trilhajusta.services;

import com.trilhajusta.domain.entities.Competencia;
import com.trilhajusta.repositories.CompetenciaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompetenciaService {

    private final CompetenciaRepository repo;

    public CompetenciaService(CompetenciaRepository repo) {
        this.repo = repo;
    }

    @Cacheable("competencias")
    public Page<Competencia> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Competencia save(Competencia c) { return repo.save(c); }
    public void delete(Long id) { repo.deleteById(id); }
    public Competencia get(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Competência não encontrada")); }
}
