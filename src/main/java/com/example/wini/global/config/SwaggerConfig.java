package com.example.wini.global.config;

import static com.example.wini.global.common.constant.SecurityConstants.HEADER_AUTHORIZATION;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(apiInfo())
                .components(securitySchemeComponents())
                .addSecurityItem(securityRequirement());
    }

    private Info apiInfo() {
        return new Info().title("Wini API 문서").version("v1.0.0");
    }

    private Components securitySchemeComponents() {
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat(HEADER_AUTHORIZATION)
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        return new Components().addSecuritySchemes(HEADER_AUTHORIZATION, bearerAuth);
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList(HEADER_AUTHORIZATION);
    }
}
