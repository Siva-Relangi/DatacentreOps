package com.datacentreops.capacity.controller;

import com.datacentreops.capacity.dto.*;
import com.datacentreops.capacity.entity.CapacitySnapshot;
import com.datacentreops.capacity.mapper.CapacitySnapshotMapper;
import com.datacentreops.capacity.service.CapacitySnapshotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capacity/snapshots")
public class CapacitySnapshotController {

    private final CapacitySnapshotService service;

    public CapacitySnapshotController(CapacitySnapshotService service) {
        this.service = service;
    }

    @PostMapping
    public CapacitySnapshotResponseDTO create(@RequestBody CapacitySnapshotRequestDTO dto) {

        return CapacitySnapshotMapper.toDTO(
                service.create(CapacitySnapshotMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<CapacitySnapshotResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(CapacitySnapshotMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CapacitySnapshotResponseDTO getById(@PathVariable Long id) {

        return CapacitySnapshotMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public CapacitySnapshotResponseDTO update(
            @PathVariable Long id,
            @RequestBody CapacitySnapshotRequestDTO dto) {

        return CapacitySnapshotMapper.toDTO(
                service.update(id, CapacitySnapshotMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/by-hall/{hallId}")
    public List<CapacitySnapshotResponseDTO> byHall(
            @PathVariable Long hallId,
            @RequestParam(required = false, defaultValue = "false") boolean currentOnly) {

        List<CapacitySnapshot> list = currentOnly
                ? service.findCurrentByHall(hallId)
                : service.findByHall(hallId);

        return list.stream()
                .map(CapacitySnapshotMapper::toDTO)
                .toList();
    }
}