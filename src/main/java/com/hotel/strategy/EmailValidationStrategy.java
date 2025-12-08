package com.hotel.strategy;

import com.hotel.model.Guest;
import org.springframework.stereotype.Component;

@Component
public class EmailValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(Guest guest) {
        if (guest.getEmail() == null || !guest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
    }
}
