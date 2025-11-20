package com.trilhajusta.messaging;

public record NovaCandidaturaEvent(Long candidaturaId, Long usuarioId, Long vagaId, String status) {}
