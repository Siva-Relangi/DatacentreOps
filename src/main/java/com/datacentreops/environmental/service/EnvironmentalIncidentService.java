package com.datacentreops.environmental.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.environmental.entity.*;
import com.datacentreops.environmental.repository.EnvironmentalIncidentRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.entity.EntityType;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.iam.repository.UserRepository;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvironmentalIncidentService {

    private final EnvironmentalIncidentRepository repository;
    private final AuditLogRepository auditLogRepository;

    public EnvironmentalIncidentService(
            EnvironmentalIncidentRepository repository,
            AuditLogRepository auditLogRepository) {

        this.repository = repository;
        this.auditLogRepository = auditLogRepository;
    }

    //  GET ALL
    public List<EnvironmentalIncident> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public EnvironmentalIncident findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EnvironmentalIncident", id));
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  RESOLVE INCIDENT (IMPORTANT)
    public EnvironmentalIncident resolve(Long id) {

        EnvironmentalIncident incident = findById(id);

        if (incident.getStatus() == IncidentStatus.RESOLVED) {
            throw new IllegalArgumentException("Incident already resolved");
        }
        
        incident.setStatus(IncidentStatus.RESOLVED);
        incident.setResolvedTime(LocalDateTime.now());

        AuditLog log = new AuditLog();
        log.setAction(AuditAction.STATUS_CHANGE);
        log.setEntityType(EntityType.ENVIRONMENTAL);
        log.setRecordId(incident.getIncidentId());
        auditLogRepository.save(log);

        return repository.save(incident);
    }

    //  SEARCH
    public List<EnvironmentalIncident> findByHall(Long hallId) {
        return repository.findByHallId(hallId);
    }

    public List<EnvironmentalIncident> findByStatus(IncidentStatus status) {
        return repository.findByStatus(status);
    }
}