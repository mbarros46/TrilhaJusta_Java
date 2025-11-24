package com.trilhajusta;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(
    info = @Info(title = "TrilhaJusta API", version = "1.0", description = "Documentação da API de Java do grupo TrilhaJusta da Global Solution 2°sem. 2025")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class TrilhaJustaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrilhaJustaApplication.class, args);
    }
}
