package com.example.datecalculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "DateCalculator API"), servers = {
        @Server(url = "http://localhost:8080", description = "Local Server")
})
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Override
    public final void addViewControllers(final ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docs", "/swagger-ui.html");
    }
}