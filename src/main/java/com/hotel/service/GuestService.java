package com.hotel.service;

import com.hotel.dao.AuditLogDAO;
import com.hotel.dao.GuestDAO;
import com.hotel.model.AuditLog;
import com.hotel.model.Guest;
import com.hotel.strategy.ValidationStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestService {

    private final GuestDAO guestDAO;
    private final AuditLogDAO auditLogDAO;
    private final List<ValidationStrategy> validationStrategies;

    public GuestService(GuestDAO guestDAO, AuditLogDAO auditLogDAO, List<ValidationStrategy> validationStrategies) {
        this.guestDAO = guestDAO;
        this.auditLogDAO = auditLogDAO;
        this.validationStrategies = validationStrategies;
    }

    @Transactional
    public void registerGuest(Guest guest) {
        // Execute Strategies
        for (ValidationStrategy strategy : validationStrategies) {
            strategy.validate(guest);
        }

        guest.setActive(true);
        guest.setCreatedAt(LocalDateTime.now());
        guestDAO.save(guest);
        logOperation("CREATE", "Registered guest: " + guest.getName());
    }

    @Transactional
    public void updateGuest(Guest guest) {
        // Simple validations for update (might skip CPF duplicate check if logic
        // handled differently)
        if (guest.getId() == null) {
            throw new IllegalArgumentException("ID obrigatório para atualização");
        }

        // Use strategies but we might need to be careful about duplicate CPF check on
        // self.
        // For simplicity, re-running all validations, but CpfStrategy handles ID check.
        for (ValidationStrategy strategy : validationStrategies) {
            strategy.validate(guest);
        }

        guestDAO.update(guest);
        logOperation("UPDATE", "Updated guest ID: " + guest.getId());
    }

    @Transactional
    public void inactivateGuest(Long id) {
        guestDAO.delete(id);
        logOperation("INACTIVATE", "Inactivated guest ID: " + id);
    }

    @Transactional
    public void deletePermanentGuest(Long id) {
        guestDAO.deletePermanent(id);
        logOperation("DELETE", "Permanently deleted guest ID: " + id);
    }

    public List<Guest> findAll() {
        return guestDAO.findAll();
    }

    public List<Guest> findByFilter(String filter) {
        return guestDAO.findByFilter(filter);
    }

    public Guest findById(Long id) {
        return guestDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Hóspede não encontrado"));
    }

    private void logOperation(String operation, String details) {
        AuditLog log = new AuditLog();
        log.setOperation(operation);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        auditLogDAO.save(log);
    }
}
