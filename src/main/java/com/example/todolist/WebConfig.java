package com.example.todolist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // указываешь путь, к которому разрешен доступ
                        .allowedOrigins("http://localhost:3000", "http://localhost:3001") // фронтенд
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // разрешенные методы
                        .allowCredentials(true)
                        .allowedHeaders("*"); // разрешенные заголовки
            }
        };
    }
}