package com.datacentreops.environmental.mapper;

import com.datacentreops.environmental.dto.*;
import com.datacentreops.environmental.entity.EnvironmentalIncident;

public class EnvironmentalIncidentMapper {

    public static EnvironmentalIncident toEntity(EnvironmentalIncidentRequestDTO dto) {

        EnvironmentalIncident e = new EnvironmentalIncident();

        e.setHallId(dto.getHallId());
        e.setIncidentType(dto.getIncidentType());
        e.setAssignedEngineerId(dto.getAssignedEngineerId());
        e.setImpactSummary(dto.getImpactSummary());

        return e;
    }

    public static EnvironmentalIncidentResponseDTO toDTO(EnvironmentalIncident entity) {

        EnvironmentalIncidentResponseDTO dto = new EnvironmentalIncidentResponseDTO();

        dto.setIncidentId(entity.getIncidentId());
        dto.setHallId(entity.getHallId());
        dto.setIncidentType(entity.getIncidentType());
        dto.setStartTime(entity.getStartTime());
        dto.setResolvedTime(entity.getResolvedTime());
        dto.setAssignedEngineerId(entity.getAssignedEngineerId());
        dto.setImpactSummary(entity.getImpactSummary());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}