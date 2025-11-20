package com.trilhajusta.services;

import com.trilhajusta.domain.entities.Vaga;
import com.trilhajusta.oracle.MatchOracleRepository;
import com.trilhajusta.repositories.VagaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MatchService {

    private final MatchOracleRepository oracleRepository;
    private final VagaRepository vagaRepository;

    public MatchService(MatchOracleRepository oracleRepository, VagaRepository vagaRepository) {
        this.oracleRepository = oracleRepository;
        this.vagaRepository = vagaRepository;
    }

    public List<VagaScore> rank(Long usuarioId, int page, int size) {
        var pageVagas = vagaRepository.findAll(PageRequest.of(page, size));
        return pageVagas.stream()
                .map(v -> new VagaScore(v, oracleRepository.scoreCompatibilidade(usuarioId, v.getId())))
                .sorted(Comparator.comparingDouble(VagaScore::score).reversed())
                .toList();
    }

    public record VagaScore(Vaga vaga, double score) {}
}
