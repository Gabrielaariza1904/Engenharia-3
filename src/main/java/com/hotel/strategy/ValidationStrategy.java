package com.hotel.strategy;

import com.hotel.model.Guest;

public interface ValidationStrategy {
    void validate(Guest guest) throws IllegalArgumentException;
}
