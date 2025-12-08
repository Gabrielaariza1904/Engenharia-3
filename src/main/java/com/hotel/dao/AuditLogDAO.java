package com.hotel.dao;

import com.hotel.model.AuditLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuditLogDAO {

    private final JdbcTemplate jdbcTemplate;

    public AuditLogDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(AuditLog log) {
        String sql = "INSERT INTO audit_logs (operation, details, timestamp) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, log.getOperation(), log.getDetails(), log.getTimestamp());
    }
}
