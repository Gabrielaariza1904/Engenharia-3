package com.hotel.dao;

import com.hotel.model.Guest;
import java.util.List;
import java.util.Optional;

public interface GuestDAO {
    void save(Guest guest);

    void update(Guest guest);

    Optional<Guest> findById(Long id);

    List<Guest> findAll();

    void delete(Long id);

    void activate(Long id);

    void deletePermanent(Long id); // Effectively inactivates

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    List<Guest> findByFilter(String filter);
}
