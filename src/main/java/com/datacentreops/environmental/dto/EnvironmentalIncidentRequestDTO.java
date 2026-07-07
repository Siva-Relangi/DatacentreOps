package com.datacentreops.environmental.dto;

import com.datacentreops.environmental.entity.IncidentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentalIncidentRequestDTO {

    @NotNull(message = "Hall is required")
    private Long hallId;

    @NotNull(message = "Incident Type is required")
    private IncidentType incidentType;

    private Long assignedEngineerId;

    @NotBlank(message = "Impact Summary is required")
    private String impactSummary;
}