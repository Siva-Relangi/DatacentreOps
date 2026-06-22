package com.datacentreops.connectivity.controller;

import com.datacentreops.connectivity.dto.*;
import com.datacentreops.connectivity.entity.*;
import com.datacentreops.connectivity.mapper.CrossConnectMapper;
import com.datacentreops.connectivity.service.CrossConnectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connectivity/cross-connects")
public class CrossConnectController {

    private final CrossConnectService service;

    public CrossConnectController(CrossConnectService service) {
        this.service = service;
    }

    @PostMapping
    public CrossConnectResponseDTO create(@RequestBody CrossConnectRequestDTO dto) {

        return CrossConnectMapper.toDTO(
                service.create(CrossConnectMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<CrossConnectResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(CrossConnectMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CrossConnectResponseDTO getById(@PathVariable Long id) {

        return CrossConnectMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public CrossConnectResponseDTO update(
            @PathVariable Long id,
            @RequestBody CrossConnectRequestDTO dto) {

        return CrossConnectMapper.toDTO(
                service.update(id, CrossConnectMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<CrossConnectResponseDTO> search(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) CrossConnectStatus status) {

        List<CrossConnect> list;

        if (customerId != null) {
            list = service.findByCustomer(customerId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(CrossConnectMapper::toDTO)
                .toList();
    }
}