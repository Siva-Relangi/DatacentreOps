package com.datacentreops.iam.controller;

import com.datacentreops.iam.dto.AuditLogResponseDTO;
import com.datacentreops.iam.entity.*;
import com.datacentreops.iam.mapper.AuditLogMapper;
import com.datacentreops.iam.service.AuditLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iam/audit-logs")
public class AuditLogController {

    private final AuditLogService service;

    public AuditLogController(AuditLogService service) {
        this.service = service;
    }

    // ✅ GET ALL
    @GetMapping
    public List<AuditLogResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(AuditLogMapper::toDTO)
                .toList();
    }

    // ✅ BY USER
    @GetMapping("/by-user/{userId}")
    public List<AuditLogResponseDTO> byUser(@PathVariable Long userId) {

        return service.findByUser(userId)
                .stream()
                .map(AuditLogMapper::toDTO)
                .toList();
    }

    // ✅ BY ENTITY
    @GetMapping("/by-entity")
    public List<AuditLogResponseDTO> byEntity(
            @RequestParam EntityType entityType,
            @RequestParam Long recordId) {

        return service.findByEntity(entityType, recordId)
                .stream()
                .map(AuditLogMapper::toDTO)
                .toList();
    }
}