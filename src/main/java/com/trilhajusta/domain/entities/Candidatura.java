package com.trilhajusta.domain.entities;

import com.trilhajusta.domain.enums.CandidaturaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "candidaturas")
@Getter @Setter
public class Candidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

    @Enumerated(EnumType.STRING)
    private CandidaturaStatus status = CandidaturaStatus.SUBMETIDA;

    private Instant createdAt = Instant.now();
}
