package com.trilhajusta.security;

import com.trilhajusta.security.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> {});
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/actuator/health").permitAll()
            .requestMatchers("/api/v1/auth/**").permitAll()
            // Swagger/OpenAPI (springdoc)
            .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
            // public GETs
            .requestMatchers(HttpMethod.GET, "/api/v1/competencias/**", "/api/v1/trilhas/**", "/api/v1/vagas/**").permitAll()
            // candidaturas: allow authenticated users to create
            .requestMatchers(HttpMethod.POST, "/api/v1/candidaturas/**").hasAnyRole("USER","ADMIN")
            // only admin can change candidatura status
            .requestMatchers(HttpMethod.PATCH, "/api/v1/candidaturas/**").hasRole("ADMIN")
            // admin-only writes for management endpoints
            .requestMatchers(HttpMethod.POST, "/api/v1/competencias/**", "/api/v1/trilhas/**", "/api/v1/cursos/**", "/api/v1/vagas/**", "/api/v1/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/v1/competencias/**", "/api/v1/trilhas/**", "/api/v1/cursos/**", "/api/v1/vagas/**", "/api/v1/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/competencias/**", "/api/v1/trilhas/**", "/api/v1/cursos/**", "/api/v1/vagas/**", "/api/v1/usuarios/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        );
        http.authenticationProvider(daoAuthProvider());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
