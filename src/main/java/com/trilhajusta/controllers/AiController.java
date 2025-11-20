package com.trilhajusta.controllers;

import com.trilhajusta.services.AiService;
import com.trilhajusta.domain.entities.Usuario;
import com.trilhajusta.repositories.UsuarioRepository;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;
    private final UsuarioRepository usuarioRepository;

    public AiController(AiService aiService, UsuarioRepository usuarioRepository) {
        this.aiService = aiService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/recomendar-trilhas")
    public String recomendar(@RequestParam(required = false) String perfil,
                             @RequestParam(required = false) Long usuarioId) {
        String perfilTexto = null;
        if (usuarioId != null) {
            Usuario u = usuarioRepository.findByIdWithCompetencias(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            String comps = u.getCompetencias().stream().map(c -> c.getNome()).collect(Collectors.joining(", "));
            perfilTexto = String.format("Nome: %s; Cidade: %s/%s; Competências: %s", u.getNome(), u.getCidade(), u.getUf(), comps);
        } else if (perfil != null && !perfil.isBlank()) {
            perfilTexto = perfil;
        } else {
            throw new RuntimeException("Informe 'perfil' ou 'usuarioId'");
        }
        return aiService.recomendarTrilhas(perfilTexto);
    }
}
