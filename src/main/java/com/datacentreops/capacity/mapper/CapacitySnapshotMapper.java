package com.datacentreops.capacity.mapper;

import com.datacentreops.capacity.dto.*;
import com.datacentreops.capacity.entity.CapacitySnapshot;

public class CapacitySnapshotMapper {

    public static CapacitySnapshot toEntity(CapacitySnapshotRequestDTO dto) {

        CapacitySnapshot s = new CapacitySnapshot();

        s.setHallId(dto.getHallId());

        if (dto.getSnapshotDate() != null) {
            s.setSnapshotDate(dto.getSnapshotDate());
        }

        s.setTotalRacks(dto.getTotalRacks());
        s.setAllocatedRacks(dto.getAllocatedRacks());
        s.setTotalPowerKW(dto.getTotalPowerKW());
        s.setAllocatedPowerKW(dto.getAllocatedPowerKW());
        s.setCoolingUsagePercent(dto.getCoolingUsagePercent());
        s.setSpaceUtilisationPercent(dto.getSpaceUtilisationPercent());
        s.setPowerUtilisationPercent(dto.getPowerUtilisationPercent());

        return s;
    }

    public static CapacitySnapshotResponseDTO toDTO(CapacitySnapshot entity) {

        CapacitySnapshotResponseDTO dto = new CapacitySnapshotResponseDTO();

        dto.setSnapshotId(entity.getSnapshotId());
        dto.setHallId(entity.getHallId());
        dto.setSnapshotDate(entity.getSnapshotDate());
        dto.setTotalRacks(entity.getTotalRacks());
        dto.setAllocatedRacks(entity.getAllocatedRacks());
        dto.setTotalPowerKW(entity.getTotalPowerKW());
        dto.setAllocatedPowerKW(entity.getAllocatedPowerKW());
        dto.setCoolingUsagePercent(entity.getCoolingUsagePercent());
        dto.setSpaceUtilisationPercent(entity.getSpaceUtilisationPercent());
        dto.setPowerUtilisationPercent(entity.getPowerUtilisationPercent());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}