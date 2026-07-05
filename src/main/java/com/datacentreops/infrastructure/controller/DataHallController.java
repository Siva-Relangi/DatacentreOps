package com.datacentreops.infrastructure.controller;

import com.datacentreops.infrastructure.dto.*;
import com.datacentreops.infrastructure.entity.*;
import com.datacentreops.infrastructure.mapper.DataHallMapper;
import com.datacentreops.infrastructure.service.DataHallService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/infrastructure/data-halls")
public class DataHallController {

    private final DataHallService service;

    public DataHallController(DataHallService service) {
        this.service = service;
    }

    @PostMapping
    public DataHallResponseDTO create(@RequestBody DataHallRequestDTO dto) {

        return DataHallMapper.toDTO(
                service.create(DataHallMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<DataHallResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(DataHallMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public DataHallResponseDTO getById(@PathVariable Long id) {

        return DataHallMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public DataHallResponseDTO update(
            @PathVariable Long id,
            @RequestBody DataHallRequestDTO dto) {

        return DataHallMapper.toDTO(
                service.update(id, DataHallMapper.toEntity(dto))
        );
    }

    @PatchMapping("/{id}/status")
    public DataHallResponseDTO changeStatus(
            @PathVariable Long id, 
            @RequestParam HallStatus status) {

        return DataHallMapper.toDTO(
                service.changeStatus(id, status)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<DataHallResponseDTO> search(
            @RequestParam(required = false) Long dataCentreId,
            @RequestParam(required = false) HallStatus status) {

        List<DataHall> list;

        if (dataCentreId != null) {
            list = service.findByDataCentre(dataCentreId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(DataHallMapper::toDTO)
                .toList();
    }
}