package com.hotel.controller;

import com.hotel.model.Guest;
import com.hotel.service.GuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public ResponseEntity<?> registerGuest(@RequestBody Guest guest) {
        try {
            guestService.registerGuest(guest);
            return ResponseEntity.ok("Hóspede registrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao registrar hóspede: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
        try {
            guest.setId(id);
            guestService.updateGuest(guest);
            return ResponseEntity.ok("Hóspede atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar hóspede: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> inactivateGuest(@PathVariable Long id) {
        try {
            guestService.inactivateGuest(id);
            return ResponseEntity.ok("Hóspede inativado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao inativar hóspede: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Guest> getGuests(@RequestParam(required = false) String filter) {
        if (filter != null && !filter.isEmpty()) {
            return guestService.findByFilter(filter);
        }
        return guestService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGuest(@PathVariable Long id) {
        try {
            Guest guest = guestService.findById(id);
            return ResponseEntity.ok(guest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
