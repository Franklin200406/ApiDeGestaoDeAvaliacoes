package br.edu.gestaoavaliacoes.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI assessmentManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Assessment Management API")
                        .version("0.0.1-SNAPSHOT")
                        .description("REST API for managing users, courses, disciplines, questions and assessments."));
    }
}