package com.hotel.strategy;

import com.hotel.model.Guest;
import org.springframework.stereotype.Component;

@Component
public class PhoneValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(Guest guest) {
        if (guest.getPhone() == null || !guest.getPhone().matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Formato de telefone inválido. Deve ter 10 ou 11 dígitos.");
        }
    }
}
