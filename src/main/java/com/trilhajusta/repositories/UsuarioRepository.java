package com.trilhajusta.repositories;

import com.trilhajusta.domain.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    Page<Usuario> findByCompetencias_NomeIgnoreCase(String nome, Pageable pageable);

    @Query("select u from Usuario u left join fetch u.competencias where u.id = :id")
    Optional<Usuario> findByIdWithCompetencias(@Param("id") Long id);
}
