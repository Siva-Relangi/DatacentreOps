package com.datacentreops.connectivity.mapper;

import com.datacentreops.connectivity.dto.*;
import com.datacentreops.connectivity.entity.CarrierPresence;

public class CarrierPresenceMapper {

    public static CarrierPresence toEntity(CarrierPresenceRequestDTO dto) {

        CarrierPresence c = new CarrierPresence();

        c.setCarrierName(dto.getCarrierName());
        c.setServiceType(dto.getServiceType());
        c.setDataCentreId(dto.getDataCentreId());
        c.setMmrCabinetId(dto.getMmrCabinetId());

        return c;
    }

    public static CarrierPresenceResponseDTO toDTO(CarrierPresence entity) {

        CarrierPresenceResponseDTO dto = new CarrierPresenceResponseDTO();

        dto.setCarrierId(entity.getCarrierId());
        dto.setCarrierName(entity.getCarrierName());
        dto.setServiceType(entity.getServiceType());
        dto.setDataCentreId(entity.getDataCentreId());
        dto.setMmrCabinetId(entity.getMmrCabinetId());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}