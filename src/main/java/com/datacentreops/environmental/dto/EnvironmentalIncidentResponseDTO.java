package com.datacentreops.environmental.dto;

import com.datacentreops.environmental.entity.IncidentStatus;
import com.datacentreops.environmental.entity.IncidentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EnvironmentalIncidentResponseDTO {

    private Long incidentId;
    private Long hallId;
    private IncidentType incidentType;
    private LocalDateTime startTime;
    private LocalDateTime resolvedTime;
    private Long assignedEngineerId;
    private String impactSummary;
    private IncidentStatus status;
}