package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Library Management System application.
 * Initializes Spring Boot and starts the application context.
 * Enables component scanning and auto-configuration.
 *
 * Features enabled by @SpringBootApplication:
 * - Component scanning
 * - Auto-configuration
 * - Property support
 * - Database connectivity
 * - Web server (Tomcat)
 * - Actuator endpoints
 */
@SpringBootApplication
public class LibraryManagementApplication {

    /**
     * Main method that starts the Library Management System.
     * Bootstraps the Spring application context.
     * Initializes all configured components:
     * - REST controllers
     * - Service layer
     * - Data repositories
     * - Database connection
     * - Swagger documentation
     *
     * Application endpoints:
     * - API: http://localhost:8080/api/
     * - Swagger UI: http://localhost:8080/
     * - Health: http://localhost:8080/actuator/health
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
}
