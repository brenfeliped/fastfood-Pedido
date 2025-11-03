package com.fastfood.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        final String securitySchemeName = "bearerAuth";


        return new OpenAPI()
                .info(new Info()
                        .title("Fastfood API")
                        .description("API de gerenciamento de pedidos, clientes e produtos de uma lanchonete.")
                        .version("v3"))
                        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                        .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .description("Insira o token JWT no formato: Bearer {token}")));
    }
}
