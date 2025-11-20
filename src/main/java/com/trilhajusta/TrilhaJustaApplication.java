package com.trilhajusta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrilhaJustaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrilhaJustaApplication.class, args);
    }
}
