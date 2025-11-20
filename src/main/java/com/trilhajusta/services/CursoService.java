package com.trilhajusta.services;

import com.trilhajusta.domain.entities.Curso;
import com.trilhajusta.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository repo;

    public CursoService(CursoRepository repo) {
        this.repo = repo;
    }

    public List<Curso> byTrilha(Long trilhaId) { return repo.findByTrilha_Id(trilhaId); }
    public Curso save(Curso c) { return repo.save(c); }
    public void delete(Long id) { repo.deleteById(id); }
    public Curso get(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado")); }
}
