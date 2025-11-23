package com.trilhajusta.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRILHA")
@Getter @Setter
public class Trilha {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trilha")
    @SequenceGenerator(name = "seq_trilha", sequenceName = "SEQ_TRILHA", allocationSize = 1)
    private Long id;

    @NotBlank(message = "{validation.notblank}")
    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursos = new ArrayList<>();
}
