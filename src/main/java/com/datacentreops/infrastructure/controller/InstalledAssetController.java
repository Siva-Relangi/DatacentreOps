package com.datacentreops.infrastructure.controller;

import com.datacentreops.infrastructure.dto.*;
import com.datacentreops.infrastructure.entity.*;
import com.datacentreops.infrastructure.mapper.InstalledAssetMapper;
import com.datacentreops.infrastructure.service.InstalledAssetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/infrastructure/assets")
public class InstalledAssetController {

    private final InstalledAssetService service;

    public InstalledAssetController(InstalledAssetService service) {
        this.service = service;
    }

    @PostMapping
    public InstalledAssetResponseDTO create(@RequestBody InstalledAssetRequestDTO dto) {

        return InstalledAssetMapper.toDTO(
                service.create(InstalledAssetMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<InstalledAssetResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(InstalledAssetMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public InstalledAssetResponseDTO getById(@PathVariable Long id) {

        return InstalledAssetMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public InstalledAssetResponseDTO update(
            @PathVariable Long id,
            @RequestBody InstalledAssetRequestDTO dto) {

        return InstalledAssetMapper.toDTO(
                service.update(id, InstalledAssetMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<InstalledAssetResponseDTO> search(
            @RequestParam(required = false) Long rackId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String assetType,
            @RequestParam(required = false) AssetStatus status) {

        List<InstalledAsset> list;

        if (rackId != null) {
            list = service.findByRack(rackId);
        } else if (customerId != null) {
            list = service.findByCustomer(customerId);
        } else if (assetType != null) {
            list = service.findByType(assetType);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(InstalledAssetMapper::toDTO)
                .toList();
    }
}