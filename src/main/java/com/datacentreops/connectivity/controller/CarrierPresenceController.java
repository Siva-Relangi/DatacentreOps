package com.datacentreops.connectivity.controller;

import com.datacentreops.connectivity.dto.*;
import com.datacentreops.connectivity.entity.*;
import com.datacentreops.connectivity.mapper.CarrierPresenceMapper;
import com.datacentreops.connectivity.service.CarrierPresenceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connectivity/carrier-presence")
public class CarrierPresenceController {

    private final CarrierPresenceService service;

    public CarrierPresenceController(CarrierPresenceService service) {
        this.service = service;
    }

    @PostMapping
    public CarrierPresenceResponseDTO create(@Valid @RequestBody CarrierPresenceRequestDTO dto) {

        return CarrierPresenceMapper.toDTO(
                service.create(CarrierPresenceMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<CarrierPresenceResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(CarrierPresenceMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CarrierPresenceResponseDTO getById(@PathVariable Long id) {

        return CarrierPresenceMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public CarrierPresenceResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody CarrierPresenceRequestDTO dto) {

        return CarrierPresenceMapper.toDTO(
                service.update(id, CarrierPresenceMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<CarrierPresenceResponseDTO> search(
            @RequestParam(required = false) Long dataCentreId,
            @RequestParam(required = false) CarrierStatus status) {

        List<CarrierPresence> list;

        if (dataCentreId != null) {
            list = service.findByDataCentre(dataCentreId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(CarrierPresenceMapper::toDTO)
                .toList();
    }
}