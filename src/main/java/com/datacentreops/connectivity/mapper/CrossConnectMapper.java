package com.datacentreops.connectivity.mapper;

import com.datacentreops.connectivity.dto.*;
import com.datacentreops.connectivity.entity.CrossConnect;

public class CrossConnectMapper {

    public static CrossConnect toEntity(CrossConnectRequestDTO dto) {

        CrossConnect c = new CrossConnect();

        c.setCustomerId(dto.getCustomerId());
        c.setRackId(dto.getRackId());
        c.setAssetId(dto.getAssetId());
        c.setConnectionType(dto.getConnectionType());
        c.setPortA(dto.getPortA());
        c.setPortZ(dto.getPortZ());
        c.setBandwidth(dto.getBandwidth());
        c.setCarrierId(dto.getCarrierId());
        c.setOrderDate(dto.getOrderDate());
        c.setProvisionedDate(dto.getProvisionedDate());
        c.setMonthlyCost(dto.getMonthlyCost());

        return c;
    }

    public static CrossConnectResponseDTO toDTO(CrossConnect entity) {

        CrossConnectResponseDTO dto = new CrossConnectResponseDTO();

        dto.setCrossConnectId(entity.getCrossConnectId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setRackId(entity.getRackId());
        dto.setAssetId(entity.getAssetId());
        dto.setConnectionType(entity.getConnectionType());
        dto.setPortA(entity.getPortA());
        dto.setPortZ(entity.getPortZ());
        dto.setBandwidth(entity.getBandwidth());
        dto.setCarrierId(entity.getCarrierId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setProvisionedDate(entity.getProvisionedDate());
        dto.setMonthlyCost(entity.getMonthlyCost());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}