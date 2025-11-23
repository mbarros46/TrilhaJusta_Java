package com.trilhajusta.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VAGA")
@Getter @Setter
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vaga")
    @SequenceGenerator(name = "seq_vaga", sequenceName = "SEQ_VAGA", allocationSize = 1)
    private Long id;

    @NotBlank(message = "{validation.notblank}")
    @Column(name = "EMPRESA")
    private String empresa;

    @NotBlank(message = "{validation.notblank}")
    @Column(name = "TITULO")
    private String titulo;

    @NotBlank(message = "{validation.notblank}")
    @Column(name = "CIDADE")
    private String cidade;

    @NotBlank(message = "{validation.notblank}")
    @Column(name = "UF")
    private String uf;

    @Column(name = "DESCRICAO", length = 2000)
    private String descricao;

    @ManyToMany
    @JoinTable(name = "VAGA_COMPETENCIA",
            joinColumns = @JoinColumn(name = "VAGA_ID"),
            inverseJoinColumns = @JoinColumn(name = "COMPETENCIA_ID"))
    private Set<Competencia> competenciasRequeridas = new HashSet<>();
}
