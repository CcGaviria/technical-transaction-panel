package com.gaviria.transaction.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Transacciones",
        description = "API para gestionar las transacciones de Tenpistas",
        version = "1.0.0"
    )
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi transactionApi() {
        return GroupedOpenApi.builder()
            .group("transaction")
            .pathsToMatch("/transactions/**")
            .build();
    }
}
