package com.trilhajusta.domain.entities;

import com.trilhajusta.domain.enums.CandidaturaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "CANDIDATURA")
@Getter @Setter
public class Candidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_candidatura")
    @SequenceGenerator(name = "seq_candidatura", sequenceName = "SEQ_CANDIDATURA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VAGA_ID")
    private Vaga vaga;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CandidaturaStatus status = CandidaturaStatus.SUBMETIDA;

    @Column(name = "CREATED_AT")
    private Instant createdAt = Instant.now();
}
