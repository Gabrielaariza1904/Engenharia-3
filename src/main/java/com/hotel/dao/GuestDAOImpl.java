package com.hotel.dao;

import com.hotel.model.Guest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public class GuestDAOImpl implements GuestDAO {

    private final JdbcTemplate jdbcTemplate;

    public GuestDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Guest> rowMapper = (rs, rowNum) -> {
        Guest guest = new Guest();
        guest.setId(rs.getLong("id"));
        guest.setName(rs.getString("name"));
        guest.setCpf(rs.getString("cpf"));

        String birthDateStr = rs.getString("birth_date");
        if (birthDateStr != null) {
            guest.setBirthDate(LocalDate.parse(birthDateStr));
        }

        guest.setPhone(rs.getString("phone"));
        guest.setEmail(rs.getString("email"));
        guest.setAddressStreet(rs.getString("address_street"));
        guest.setAddressNumber(rs.getString("address_number"));
        guest.setAddressZip(rs.getString("address_zip"));
        guest.setAddressNeighborhood(rs.getString("address_neighborhood"));
        guest.setAddressComplement(rs.getString("address_complement"));
        guest.setAddressCity(rs.getString("address_city"));
        guest.setAddressState(rs.getString("address_state"));
        guest.setActive(rs.getInt("active") == 1);

        String createdAtStr = rs.getString("created_at");
        if (createdAtStr != null) {
            guest.setCreatedAt(LocalDateTime.parse(createdAtStr));
        }

        return guest;
    };

    @Override
    public void save(Guest guest) {
        String sql = "INSERT INTO guests (name, cpf, birth_date, phone, email, address_street, address_number, address_zip, address_neighborhood, address_complement, address_city, address_state, active, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, guest.getName(), guest.getCpf(), guest.getBirthDate(), guest.getPhone(),
                guest.getEmail(), guest.getAddressStreet(), guest.getAddressNumber(), guest.getAddressZip(),
                guest.getAddressNeighborhood(), guest.getAddressComplement(), guest.getAddressCity(),
                guest.getAddressState(), guest.isActive(), guest.getCreatedAt());
    }

    @Override
    public void update(Guest guest) {
        String sql = "UPDATE guests SET name = ?, cpf = ?, birth_date = ?, phone = ?, email = ?, address_street = ?, address_number = ?, address_zip = ?, address_neighborhood = ?, address_complement = ?, address_city = ?, address_state = ? WHERE id = ?";
        jdbcTemplate.update(sql, guest.getName(), guest.getCpf(), guest.getBirthDate(), guest.getPhone(),
                guest.getEmail(),
                guest.getAddressStreet(), guest.getAddressNumber(), guest.getAddressZip(),
                guest.getAddressNeighborhood(), guest.getAddressComplement(), guest.getAddressCity(),
                guest.getAddressState(), guest.getId());
    }

    @Override
    public Optional<Guest> findById(Long id) {
        String sql = "SELECT * FROM guests WHERE id = ?";
        List<Guest> guests = jdbcTemplate.query(sql, rowMapper, id);
        return guests.stream().findFirst();
    }

    @Override
    public List<Guest> findAll() {
        String sql = "SELECT * FROM guests ORDER BY name";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE guests SET active = false WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void activate(Long id) {
        String sql = "UPDATE guests SET active = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM guests WHERE cpf = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }

    @Override
    public List<Guest> findByFilter(String filter) {
        String sql = "SELECT * FROM guests WHERE LOWER(name) LIKE LOWER(?) OR cpf LIKE ?";
        String wildCard = "%" + filter + "%";
        return jdbcTemplate.query(sql, rowMapper, wildCard, wildCard);
    }

    @Override
    public boolean existsByCpfAndIdNot(String cpf, Long id) {
        String sql = "SELECT COUNT(*) FROM guests WHERE cpf = ? AND id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf, id);
        return count != null && count > 0;
    }

    @Override
    public void deletePermanent(Long id) {
        String sql = "DELETE FROM guests WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
