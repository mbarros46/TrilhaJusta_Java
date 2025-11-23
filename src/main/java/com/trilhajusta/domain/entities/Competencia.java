package com.trilhajusta.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "COMPETENCIA")
@Getter @Setter
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_competencia")
    @SequenceGenerator(name = "seq_competencia", sequenceName = "SEQ_COMPETENCIA", allocationSize = 1)
    private Long id;

    @NotBlank(message = "{validation.notblank}")
    @Column(name = "NOME", unique = true)
    private String nome;

    @Column(name = "DESCRICAO")
    private String area;

    @Transient
    private Integer nivelPadrao = 1;
}
