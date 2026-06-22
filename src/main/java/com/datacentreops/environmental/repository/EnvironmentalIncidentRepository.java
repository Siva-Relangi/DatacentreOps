package com.datacentreops.environmental.repository;

import com.datacentreops.environmental.entity.EnvironmentalIncident;
import com.datacentreops.environmental.entity.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvironmentalIncidentRepository extends JpaRepository<EnvironmentalIncident, Long> {

    List<EnvironmentalIncident> findByHallId(Long hallId);

    List<EnvironmentalIncident> findByStatus(IncidentStatus status);

    List<EnvironmentalIncident> findByAssignedEngineerId(Long assignedEngineerId);
}