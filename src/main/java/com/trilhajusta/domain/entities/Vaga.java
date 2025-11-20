package com.trilhajusta.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vagas")
@Getter @Setter
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String empresa;

    @NotBlank
    private String titulo;

    @NotBlank
    private String cidade;

    @NotBlank
    private String uf;

    @Column(length = 2000)
    private String descricao;

    @ManyToMany
    @JoinTable(name = "vaga_competencia",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "competencia_id"))
    private Set<Competencia> competenciasRequeridas = new HashSet<>();
}
