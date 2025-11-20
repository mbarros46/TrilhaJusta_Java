package com.trilhajusta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 6, max = 100) String senha,
        @NotBlank String cidade,
        @NotBlank String uf
) {}
