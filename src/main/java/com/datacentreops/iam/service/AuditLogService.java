package com.datacentreops.iam.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.iam.entity.*;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.iam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository repository;
    private final UserRepository userRepository;

    public AuditLogService(AuditLogRepository repository,
                           UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    //  CREATE (used internally)
    public AuditLog create(AuditLog log) {

        if (log.getUserId() != null &&
                !userRepository.existsById(log.getUserId())) {
            throw new ResourceNotFoundException("User", log.getUserId());
        }

        return repository.save(log);
    }

    //  GET ALL
    public List<AuditLog> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public AuditLog findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog", id));
    }

    //  FIND BY USER
    public List<AuditLog> findByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    //  FIND BY ENTITY
    public List<AuditLog> findByEntity(EntityType entityType, Long recordId) {
        return repository.findByEntityTypeAndRecordId(entityType, recordId);
    }
}