package com.hotel.strategy;

import com.hotel.dao.GuestDAO;
import com.hotel.model.Guest;
import org.springframework.stereotype.Component;

@Component
public class CpfValidationStrategy implements ValidationStrategy {

    private final GuestDAO guestDAO;

    public CpfValidationStrategy(GuestDAO guestDAO) {
        this.guestDAO = guestDAO;
    }

    @Override
    public void validate(Guest guest) {
        if (guest.getCpf() == null || !guest.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("Formato de CPF inválido. Deve conter 11 dígitos.");
        }

        if (guest.getId() == null) {
            // New Guest
            if (guestDAO.existsByCpf(guest.getCpf())) {
                throw new IllegalArgumentException("CPF já cadastrado.");
            }
        } else {
            // Updating Guest
            if (guestDAO.existsByCpfAndIdNot(guest.getCpf(), guest.getId())) {
                throw new IllegalArgumentException("CPF já está em uso por outro hóspede.");
            }
        }
    }
}
