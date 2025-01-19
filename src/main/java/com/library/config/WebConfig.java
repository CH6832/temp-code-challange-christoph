package com.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration class for the library management system.
 * Configures Cross-Origin Resource Sharing (CORS) settings.
 * Enables cross-origin requests for the API endpoints.
 * Implements WebMvcConfigurer to customize Spring MVC configuration.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     * Allows cross-origin requests to all API endpoints (/api/**).
     *
     * Configuration details:
     * - Allows requests from any origin (*)
     * - Supports standard HTTP methods (GET, POST, PUT, DELETE, OPTIONS)
     * - Allows all headers
     * - Sets max age to 1 hour (3600 seconds)
     *
     * @param registry CorsRegistry to configure CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {  // Changed from addCorsCrossOrigins to addCorsMappings
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
