package com.datacentreops.environmental.dto;

import com.datacentreops.environmental.entity.IncidentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentalIncidentRequestDTO {

    private Long hallId;
    private IncidentType incidentType;
    private Long assignedEngineerId;
    private String impactSummary;
}