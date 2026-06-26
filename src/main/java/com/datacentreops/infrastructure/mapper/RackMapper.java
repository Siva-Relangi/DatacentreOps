package com.datacentreops.infrastructure.mapper;

import com.datacentreops.infrastructure.dto.*;
import com.datacentreops.infrastructure.entity.Rack;

public class RackMapper {

    public static Rack toEntity(RackRequestDTO dto) {

        Rack r = new Rack();

        r.setHallId(dto.getHallId());
        r.setRackLabel(dto.getRackLabel());
        r.setTotalU(dto.getTotalU());
        r.setMaxPowerKW(dto.getMaxPowerKW());

        return r;
    }

    public static RackResponseDTO toDTO(Rack entity) {

        RackResponseDTO dto = new RackResponseDTO();

        dto.setRackId(entity.getRackId());
        dto.setHallId(entity.getHallId());
        dto.setRackLabel(entity.getRackLabel());
        dto.setTotalU(entity.getTotalU());
        dto.setUsedU(entity.getUsedU());
        dto.setAvailableU(entity.getAvailableU());
        dto.setMaxPowerKW(entity.getMaxPowerKW());
        dto.setAllocatedPowerKW(entity.getAllocatedPowerKW());
        dto.setCustomerId(entity.getCustomerId());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}