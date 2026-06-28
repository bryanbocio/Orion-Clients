package com.oriontek.clients.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class OpenApiConfig {
      @Bean
    public OpenAPI orionTekOpenApi() {
        return new OpenAPI().info(new Info()
                .title("OrionTek Clients API")
                .license(new License().name("Prueba técnica")));
    }
}
