package org.example.apikhiata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Define o caminho que terá CORS habilitado
                .allowedOrigins("http://localhost:3000") // Permite requisições de localhost:3000
                .allowedMethods("*") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true) // Permite envio de cookies/credenciais
                .maxAge(3600); // Define por quanto tempo a resposta CORS pode ser armazenada em cache pelo navegador
    }
}
