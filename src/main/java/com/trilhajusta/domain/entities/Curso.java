package com.trilhajusta.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CURSO")
@Getter @Setter
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_curso")
    @SequenceGenerator(name = "seq_curso", sequenceName = "SEQ_CURSO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRILHA_ID")
    private Trilha trilha;

    @NotBlank(message = "{validation.notblank}")
    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "DESCRICAO")
    private String provedor;

    @Transient
    private Integer cargaHoraria;
}
