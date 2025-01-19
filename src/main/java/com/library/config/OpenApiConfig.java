package com.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI/Swagger documentation.
 * Provides customized OpenAPI documentation for the library management system.
 * Configures API information, versioning, and contact details.
 * Makes API documentation available at the base URL.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates and configures the OpenAPI documentation bean.
     * Sets up API information including:
     * - Title: Library Management API
     * - Version: 1.0
     * - Description: Overview of available endpoints
     * - Contact information for the development team
     *
     * The documentation is accessible through:
     * - Swagger UI: /swagger-ui.html
     * - OpenAPI JSON: /v3/api-docs
     *
     * @return configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management API")
                        .version("1.0")
                        .description("API for managing books, authors, members, and loans")
                        .contact(new Contact()
                                .name("Library Team")
                                .email("library@example.com")));
    }
}
