package com.datacentreops.environmental.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.environmental.entity.*;
import com.datacentreops.environmental.repository.EnvironmentalIncidentRepository;
import com.datacentreops.iam.repository.UserRepository;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvironmentalIncidentService {

    private final EnvironmentalIncidentRepository repository;
    private final DataHallRepository dataHallRepository;
    private final UserRepository userRepository;

    public EnvironmentalIncidentService(
            EnvironmentalIncidentRepository repository,
            DataHallRepository dataHallRepository,
            UserRepository userRepository) {

        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
        this.userRepository = userRepository;
    }

    //  CREATE
    public EnvironmentalIncident create(EnvironmentalIncident entity) {

        validate(entity);
        return repository.save(entity);
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

    //  UPDATE
    public EnvironmentalIncident update(Long id, EnvironmentalIncident s) {

        EnvironmentalIncident existing = findById(id);

        existing.setHallId(s.getHallId());
        existing.setIncidentType(s.getIncidentType());
        existing.setAssignedEngineerId(s.getAssignedEngineerId());
        existing.setImpactSummary(s.getImpactSummary());
        existing.setResolvedTime(s.getResolvedTime());
        existing.setStatus(s.getStatus());

        validate(existing);
        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION
    private void validate(EnvironmentalIncident entity) {

        if (!dataHallRepository.existsById(entity.getHallId())) {
            throw new ResourceNotFoundException("DataHall", entity.getHallId());
        }

        if (entity.getAssignedEngineerId() != null &&
                !userRepository.existsById(entity.getAssignedEngineerId())) {
            throw new ResourceNotFoundException("User", entity.getAssignedEngineerId());
        }

        if (entity.getResolvedTime() != null &&
                entity.getResolvedTime().isBefore(entity.getStartTime())) {
            throw new IllegalArgumentException("resolvedTime cannot be before startTime");
        }
    }

    //  RESOLVE INCIDENT (IMPORTANT)
    public EnvironmentalIncident resolve(Long id) {

        EnvironmentalIncident incident = findById(id);

        incident.setStatus(IncidentStatus.RESOLVED);
        incident.setResolvedTime(LocalDateTime.now());

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