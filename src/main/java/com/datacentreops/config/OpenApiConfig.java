package com.datacentreops.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI metadata + a JWT "bearer" security scheme so the
 * Swagger UI shows an "Authorize" button for testing protected endpoints.
 */
@Configuration
public class OpenApiConfig {

    private static final String SCHEME = "bearerAuth";

    @Bean
    public OpenAPI dataCentreOpsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DataCentreOps API")
                        .version("1.0")
                        .description("Data Centre & Colocation Management Platform - REST API backend. "
                                + "Login via /api/auth/login to get a JWT, click Authorize, then call protected endpoints."))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME))
                .components(new Components().addSecuritySchemes(SCHEME,
                        new SecurityScheme()
                                .name(SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
