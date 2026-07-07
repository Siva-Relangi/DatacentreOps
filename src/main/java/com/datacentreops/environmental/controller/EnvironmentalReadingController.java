package com.datacentreops.environmental.controller;

import com.datacentreops.environmental.dto.*;
import com.datacentreops.environmental.entity.*;
import com.datacentreops.environmental.mapper.EnvironmentalReadingMapper;
import com.datacentreops.environmental.service.EnvironmentalReadingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environmental/readings")
public class EnvironmentalReadingController {

    private final EnvironmentalReadingService service;

    public EnvironmentalReadingController(EnvironmentalReadingService service) {
        this.service = service;
    }

    @PostMapping
    public EnvironmentalReadingResponseDTO create(@Valid @RequestBody EnvironmentalReadingRequestDTO dto) {

        return EnvironmentalReadingMapper.toDTO(
                service.create(EnvironmentalReadingMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<EnvironmentalReadingResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(EnvironmentalReadingMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public EnvironmentalReadingResponseDTO getById(@PathVariable Long id) {

        return EnvironmentalReadingMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public EnvironmentalReadingResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody EnvironmentalReadingRequestDTO dto) {

        return EnvironmentalReadingMapper.toDTO(
                service.update(id, EnvironmentalReadingMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<EnvironmentalReadingResponseDTO> search(
            @RequestParam(required = false) Long hallId,
            @RequestParam(required = false) ReadingType readingType,
            @RequestParam(required = false) ReadingStatus status) {

        List<EnvironmentalReading> list;

        if (hallId != null && readingType != null) {
            list = service.findByHallAndType(hallId, readingType);
        } else if (hallId != null) {
            list = service.findByHall(hallId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(EnvironmentalReadingMapper::toDTO)
                .toList();
    }
}