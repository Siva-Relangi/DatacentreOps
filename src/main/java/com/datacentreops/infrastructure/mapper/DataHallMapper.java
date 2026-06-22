package com.datacentreops.infrastructure.mapper;

import com.datacentreops.infrastructure.dto.*;
import com.datacentreops.infrastructure.entity.DataHall;

public class DataHallMapper {

    public static DataHall toEntity(DataHallRequestDTO dto) {

        DataHall d = new DataHall();

        d.setDataCentreId(dto.getDataCentreId());
        d.setHallName(dto.getHallName());
        d.setTotalRacks(dto.getTotalRacks());
        d.setTotalPowerKW(dto.getTotalPowerKW());
        d.setCoolingCapacityKW(dto.getCoolingCapacityKW());
        d.setTierLevel(dto.getTierLevel());

        return d;
    }

    public static DataHallResponseDTO toDTO(DataHall entity) {

        DataHallResponseDTO dto = new DataHallResponseDTO();

        dto.setHallId(entity.getHallId());
        dto.setDataCentreId(entity.getDataCentreId());
        dto.setHallName(entity.getHallName());
        dto.setTotalRacks(entity.getTotalRacks());
        dto.setTotalPowerKW(entity.getTotalPowerKW());
        dto.setCoolingCapacityKW(entity.getCoolingCapacityKW());
        dto.setTierLevel(entity.getTierLevel());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}