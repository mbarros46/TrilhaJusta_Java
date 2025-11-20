package com.trilhajusta.dto;

import com.trilhajusta.domain.enums.CandidaturaStatus;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusRequest(@NotNull CandidaturaStatus status) {}
