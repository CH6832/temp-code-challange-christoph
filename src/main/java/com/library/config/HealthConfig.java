package com.library.config;

import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.health.HealthIndicator;

import javax.sql.DataSource;

/**
 * Configuration class for health monitoring endpoints.
 * Configures Spring Boot Actuator health indicators.
 * Provides database connection health monitoring.
 * Accessible through the /actuator/health endpoint.
 */
@Configuration
public class HealthConfig {

    /**
     * Creates a health indicator for database connectivity monitoring.
     * Checks database connection status using:
     * - Connection availability
     * - Query execution capability
     * - Connection pool status
     *
     * The health status is exposed at:
     * - /actuator/health (summarized)
     * - /actuator/health/db (detailed)
     *
     * @param dataSource the configured DataSource to monitor
     * @return HealthIndicator for database monitoring
     */
    @Bean
    public HealthIndicator databaseHealthIndicator(DataSource dataSource) {
        return new DataSourceHealthIndicator(dataSource);
    }
}