package com.datacentreops.infrastructure.controller;

import com.datacentreops.infrastructure.dto.*;
import com.datacentreops.infrastructure.entity.*;
import com.datacentreops.infrastructure.mapper.RackMapper;
import com.datacentreops.infrastructure.service.RackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/infrastructure/racks")
public class RackController {

    private final RackService service;

    public RackController(RackService service) {
        this.service = service;
    }

    @PostMapping
    public RackResponseDTO create(@RequestBody RackRequestDTO dto) {

        return RackMapper.toDTO(
                service.create(RackMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<RackResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(RackMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public RackResponseDTO getById(@PathVariable Long id) {

        return RackMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public RackResponseDTO update(
            @PathVariable Long id,
            @RequestBody RackRequestDTO dto) {

        return RackMapper.toDTO(
                service.update(id, RackMapper.toEntity(dto))
        );
    }

    @PatchMapping("/{id}/status")
    public RackResponseDTO changeStatus(@PathVariable Long id, @RequestParam RackStatus status){
        return RackMapper.toDTO(service.changeStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<RackResponseDTO> search(
            @RequestParam(required = false) Long hallId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) RackStatus status) {

        List<Rack> list;

        if (hallId != null) {
            list = service.findByHall(hallId);
        } else if (customerId != null) {
            list = service.findByCustomer(customerId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(RackMapper::toDTO)
                .toList();
    }
}