package com.datacentreops.capacity.mapper;

import com.datacentreops.capacity.dto.*;
import com.datacentreops.capacity.entity.CapacityReservation;

public class CapacityReservationMapper {

    public static CapacityReservation toEntity(CapacityReservationRequestDTO dto) {

        CapacityReservation r = new CapacityReservation();
        r.setCustomerId(dto.getCustomerId());
        r.setHallId(dto.getHallId());
        r.setReservedU(dto.getReservedU());
        r.setReservedPowerKW(dto.getReservedPowerKW());

        if (dto.getReservationDate() != null) {
            r.setReservationDate(dto.getReservationDate());
        }

        r.setExpiryDate(dto.getExpiryDate());

        return r;
    }

    public static CapacityReservationResponseDTO toDTO(CapacityReservation entity) {

        CapacityReservationResponseDTO dto = new CapacityReservationResponseDTO();

        dto.setReservationId(entity.getReservationId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setHallId(entity.getHallId());
        dto.setReservedU(entity.getReservedU());
        dto.setReservedPowerKW(entity.getReservedPowerKW());
        dto.setReservationDate(entity.getReservationDate());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}