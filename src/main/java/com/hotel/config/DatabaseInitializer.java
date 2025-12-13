package com.hotel.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Initializing SQLite database...");

        String createGuestsTable = """
                    CREATE TABLE IF NOT EXISTS guests (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name VARCHAR(255) NOT NULL,
                        cpf VARCHAR(14) NOT NULL UNIQUE,
                        birth_date DATE,
                        phone VARCHAR(20),
                        email VARCHAR(255),
                        address_street VARCHAR(255),
                        address_number VARCHAR(20),
                        address_zip VARCHAR(20),
                        address_neighborhood VARCHAR(255),
                        address_complement VARCHAR(255),
                        address_city VARCHAR(255),
                        address_state VARCHAR(2),
                        active BOOLEAN DEFAULT 1,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;

        String createAuditLogsTable = """
                    CREATE TABLE IF NOT EXISTS audit_logs (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        operation VARCHAR(50),
                        details VARCHAR(255),
                        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;

        try {
            jdbcTemplate.execute(createGuestsTable);
            jdbcTemplate.execute(createAuditLogsTable);
            logger.info("Database tables created successfully.");
        } catch (Exception e) {
            logger.error("Error creating database tables", e);
        }
    }
}
