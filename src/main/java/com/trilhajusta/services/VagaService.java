package com.trilhajusta.services;

import com.trilhajusta.domain.entities.Vaga;
import com.trilhajusta.repositories.VagaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VagaService {

    private final VagaRepository repo;

    public VagaService(VagaRepository repo) {
        this.repo = repo;
    }

    @Cacheable("vagas")
    public Page<Vaga> list(Pageable pageable) { return repo.findAll(pageable); }

    public Page<Vaga> byLocal(String cidade, String uf, Pageable pageable) {
        return repo.findByCidadeIgnoreCaseAndUfIgnoreCase(cidade, uf, pageable);
    }

    public Page<Vaga> byCompetencia(String nome, Pageable pageable) {
        return repo.findByCompetenciasRequeridas_NomeIgnoreCase(nome, pageable);
    }

    @CacheEvict(value = "vagas", allEntries = true)
    public Vaga save(Vaga v) { return repo.save(v); }

    @CacheEvict(value = "vagas", allEntries = true)
    public void delete(Long id) { repo.deleteById(id); }

    public Vaga get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vaga não encontrada"));
    }
}
