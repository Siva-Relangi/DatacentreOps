package com.datacentreops.capacity.controller;

import com.datacentreops.capacity.dto.*;
import com.datacentreops.capacity.entity.CapacityReservation;
import com.datacentreops.capacity.entity.ReservationStatus;
import com.datacentreops.capacity.mapper.CapacityReservationMapper;
import com.datacentreops.capacity.service.CapacityReservationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capacity/reservations")
public class CapacityReservationController {

    private final CapacityReservationService service;

    public CapacityReservationController(CapacityReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<CapacityReservationResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(CapacityReservationMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CapacityReservationResponseDTO getById(@PathVariable Long id) {

        return CapacityReservationMapper.toDTO(
                service.findById(id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<CapacityReservationResponseDTO> search(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long hallId,
            @RequestParam(required = false) ReservationStatus status) {

        List<CapacityReservation> list;

        if (customerId != null) {
            list = service.findByCustomer(customerId);
        }
        else if (hallId != null) {
            list = service.findByHall(hallId);
        }
        else if (status != null) {
            list = service.findByStatus(status);
        }
        else {
            list = service.findAll();
        }

        return list.stream()
                .map(CapacityReservationMapper::toDTO)
                .toList();
    }
}