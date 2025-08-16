package com.example.wini.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().addServersItem(new Server().url("/")).info(apiInfo());
  }

  private Info apiInfo() {
    return new Info().title("Wini API 문서").version("v1.0.0");
  }
}
