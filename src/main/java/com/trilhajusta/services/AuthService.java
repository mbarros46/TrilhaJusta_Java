package com.trilhajusta.services;

import com.trilhajusta.domain.entities.Usuario;
import com.trilhajusta.dto.LoginRequest;
import com.trilhajusta.dto.SignupRequest;
import com.trilhajusta.repositories.UsuarioRepository;
import com.trilhajusta.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void signup(SignupRequest req) {
        if (usuarioRepository.findByEmail(req.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        Usuario u = new Usuario();
        u.setNome(req.nome());
        u.setEmail(req.email());
        u.setPasswordHash(passwordEncoder.encode(req.senha()));
        u.setCidade(req.cidade());
        u.setUf(req.uf());
        u.setRole("ROLE_USER");
        usuarioRepository.save(u);
    }

    public String login(LoginRequest req) {
        var authToken = new UsernamePasswordAuthenticationToken(req.email(), req.senha());
        authenticationManager.authenticate(authToken);
        var userDetails = usuarioRepository.findByEmail(req.email()).orElseThrow();
        return jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                userDetails.getEmail(), userDetails.getPasswordHash(),
                java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(userDetails.getRole()))
        ));
    }
}
