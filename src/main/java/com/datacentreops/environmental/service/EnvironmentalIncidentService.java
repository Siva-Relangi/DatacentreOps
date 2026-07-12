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

    public EnvironmentalIncidentService(
            EnvironmentalIncidentRepository repository) {

        this.repository = repository;
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