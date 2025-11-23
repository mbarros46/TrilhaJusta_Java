package com.trilhajusta.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USUARIO")
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    private Long id;

    @NotBlank(message = "{validation.notblank}")
    private String nome;

    @Email(message = "{validation.email}")
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @NotBlank(message = "{validation.notblank}")
    private String cidade;

    @NotBlank(message = "{validation.notblank}")
    private String uf;

    @Column(name = "ROLE", nullable = false)
    private String role = "ROLE_USER";

    @Column(name = "CREATED_AT")
    private Instant createdAt = Instant.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USUARIO_COMPETENCIA",
            joinColumns = @JoinColumn(name = "USUARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "COMPETENCIA_ID"))
    private Set<Competencia> competencias = new HashSet<>();
}
