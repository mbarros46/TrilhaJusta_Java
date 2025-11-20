package com.trilhajusta.repositories;

import com.trilhajusta.domain.entities.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Page<Vaga> findByCidadeIgnoreCaseAndUfIgnoreCase(String cidade, String uf, Pageable pageable);
    Page<Vaga> findByCompetenciasRequeridas_NomeIgnoreCase(String nome, Pageable pageable);
}
