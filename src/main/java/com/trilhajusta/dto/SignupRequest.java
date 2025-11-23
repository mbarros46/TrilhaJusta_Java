package com.trilhajusta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "{validation.notblank}") String nome,
        @Email(message = "{validation.email}") @NotBlank(message = "{validation.notblank}") String email,
        @NotBlank(message = "{validation.notblank}") @Size(min = 6, max = 100) String senha,
        @NotBlank(message = "{validation.notblank}") String cidade,
        @NotBlank(message = "{validation.notblank}") String uf
) {}
