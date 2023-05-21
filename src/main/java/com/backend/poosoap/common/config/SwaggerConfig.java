package com.backend.poosoap.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public OpenAPI whaiOpenAPI() {
        return new OpenAPI().info(info())
                .components(components());
    }

    private Info info() {
        return new Info()
                .title("Poosoap API")
                .description("Poosoap swagger config")
                .version("1.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT"));
    }
}
