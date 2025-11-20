package com.trilhajusta.services;

import com.trilhajusta.domain.entities.Trilha;
import com.trilhajusta.repositories.TrilhaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TrilhaService {

    private final TrilhaRepository repo;

    public TrilhaService(TrilhaRepository repo) {
        this.repo = repo;
    }

    @Cacheable("trilhas")
    public Page<Trilha> list(Pageable pageable) { return repo.findAll(pageable); }
    public Trilha save(Trilha t) { return repo.save(t); }
    public void delete(Long id) { repo.deleteById(id); }
    public Trilha get(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Trilha não encontrada")); }
}
