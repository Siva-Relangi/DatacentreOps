package com.datacentreops.environmental.controller;

import com.datacentreops.environmental.dto.*;
import com.datacentreops.environmental.entity.*;
import com.datacentreops.environmental.mapper.EnvironmentalIncidentMapper;
import com.datacentreops.environmental.service.EnvironmentalIncidentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environmental/incidents")
public class EnvironmentalIncidentController {

    private final EnvironmentalIncidentService service;

    public EnvironmentalIncidentController(EnvironmentalIncidentService service) {
        this.service = service;
    }

    @GetMapping
    public List<EnvironmentalIncidentResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(EnvironmentalIncidentMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public EnvironmentalIncidentResponseDTO getById(@PathVariable Long id) {

        return EnvironmentalIncidentMapper.toDTO(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/resolve")
    public EnvironmentalIncidentResponseDTO resolve(@PathVariable Long id) {

        return EnvironmentalIncidentMapper.toDTO(
                service.resolve(id)
        );
    }

    @GetMapping("/search")
    public List<EnvironmentalIncidentResponseDTO> search(
            @RequestParam(required = false) Long hallId,
            @RequestParam(required = false) IncidentStatus status) {

        List<EnvironmentalIncident> list;

        if (hallId != null) {
            list = service.findByHall(hallId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(EnvironmentalIncidentMapper::toDTO)
                .toList();
    }
}