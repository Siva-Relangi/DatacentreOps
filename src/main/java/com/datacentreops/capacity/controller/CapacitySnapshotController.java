package com.datacentreops.capacity.controller;

import com.datacentreops.capacity.dto.CapacitySnapshotResponseDTO;
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

    @GetMapping
    public List<CapacitySnapshotResponseDTO> getAllSnapshots() {
        return service.getAllSnapshots();
    }

    @GetMapping("/{hallId}")
    public CapacitySnapshotResponseDTO getSnapshotByHall(@PathVariable Long hallId) {
        return service.getSnapshotByHall(hallId);
    }
}