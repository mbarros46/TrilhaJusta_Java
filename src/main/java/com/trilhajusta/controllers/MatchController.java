package com.trilhajusta.controllers;

import com.trilhajusta.services.MatchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/match")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/usuarios/{usuarioId}/vagas")
    public java.util.List<MatchService.VagaScore> rank(@PathVariable Long usuarioId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "50") int size) {
        return matchService.rank(usuarioId, page, size);
    }
}
