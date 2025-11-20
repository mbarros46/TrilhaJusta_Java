package com.trilhajusta.services;

import com.trilhajusta.domain.entities.Candidatura;
import com.trilhajusta.domain.entities.Usuario;
import com.trilhajusta.domain.entities.Vaga;
import com.trilhajusta.domain.enums.CandidaturaStatus;
import com.trilhajusta.messaging.NovaCandidaturaEvent;
import com.trilhajusta.messaging.NovaCandidaturaPublisher;
import com.trilhajusta.repositories.CandidaturaRepository;
import com.trilhajusta.repositories.UsuarioRepository;
import com.trilhajusta.repositories.VagaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CandidaturaService {

    private final CandidaturaRepository repo;
    private final UsuarioRepository usuarioRepository;
    private final VagaRepository vagaRepository;
    private final NovaCandidaturaPublisher publisher;

    public CandidaturaService(CandidaturaRepository repo, UsuarioRepository usuarioRepository,
                              VagaRepository vagaRepository, NovaCandidaturaPublisher publisher) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;
        this.vagaRepository = vagaRepository;
        this.publisher = publisher;
    }

    @Transactional
    public Candidatura criar(Long usuarioId, Long vagaId) {
        Usuario u = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Vaga v = vagaRepository.findById(vagaId).orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
        Candidatura c = new Candidatura();
        c.setUsuario(u);
        c.setVaga(v);
        c.setStatus(CandidaturaStatus.SUBMETIDA);
        Candidatura saved = repo.save(c);
        publisher.publish(new NovaCandidaturaEvent(saved.getId(), u.getId(), v.getId(), saved.getStatus().name()));
        return saved;
    }

    @Transactional
    public Candidatura atualizarStatus(Long id, CandidaturaStatus status) {
        Candidatura c = repo.findById(id).orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));
        c.setStatus(status);
        return repo.save(c);
    }

    public void delete(Long id) { repo.deleteById(id); }

    public Candidatura get(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Candidatura não encontrada")); }

    public java.util.List<Candidatura> list() { return repo.findAll(); }
}
