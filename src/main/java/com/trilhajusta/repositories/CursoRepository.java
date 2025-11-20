package com.trilhajusta.repositories;

import com.trilhajusta.domain.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByTrilha_Id(Long trilhaId);
}
