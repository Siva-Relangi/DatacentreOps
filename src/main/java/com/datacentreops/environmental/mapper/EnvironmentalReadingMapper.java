package com.datacentreops.environmental.mapper;

import com.datacentreops.environmental.dto.*;
import com.datacentreops.environmental.entity.EnvironmentalReading;

public class EnvironmentalReadingMapper {

    public static EnvironmentalReading toEntity(EnvironmentalReadingRequestDTO dto) {

        EnvironmentalReading r = new EnvironmentalReading();

        r.setHallId(dto.getHallId());
        r.setSensorId(dto.getSensorId());
        r.setReadingType(dto.getReadingType());
        r.setValue(dto.getValue());
        r.setUnit(dto.getUnit());

        return r;
    }

    public static EnvironmentalReadingResponseDTO toDTO(EnvironmentalReading entity) {

        EnvironmentalReadingResponseDTO dto = new EnvironmentalReadingResponseDTO();

        dto.setReadingId(entity.getReadingId());
        dto.setHallId(entity.getHallId());
        dto.setSensorId(entity.getSensorId());
        dto.setReadingType(entity.getReadingType());
        dto.setValue(entity.getValue());
        dto.setUnit(entity.getUnit());
        dto.setReadingTime(entity.getReadingTime());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}