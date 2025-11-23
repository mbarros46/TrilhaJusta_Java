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

    @NotBlank(message = "{validation.notblank}")
    private String empresa;

    @NotBlank(message = "{validation.notblank}")
    private String titulo;

    @NotBlank(message = "{validation.notblank}")
    private String cidade;

    @NotBlank(message = "{validation.notblank}")
    private String uf;

    @Column(length = 2000)
    private String descricao;

    @ManyToMany
    @JoinTable(name = "vaga_competencia",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "competencia_id"))
    private Set<Competencia> competenciasRequeridas = new HashSet<>();
}
