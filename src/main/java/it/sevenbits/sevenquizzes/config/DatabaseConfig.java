package it.sevenbits.sevenquizzes.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Database config
 */
@Configuration
public class DatabaseConfig {
    /**
     * Returns data source
     *
     * @return data source
     */
    @Bean
    @FlywayDataSource
    @Qualifier("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Returns jdbc operations
     *
     * @param dataSource - data source
     * @return jdbc operations
     */
    @Bean
    @Qualifier("jdbcOperations")
    public JdbcOperations jdbcOperations(@Qualifier("dataSource") final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}