package com.trilhajusta.controllers;

import com.trilhajusta.dto.LoginRequest;
import com.trilhajusta.dto.SignupRequest;
import com.trilhajusta.dto.TokenResponse;
import com.trilhajusta.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest req) {
        authService.signup(req);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest req) {
        String token = authService.login(req);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
