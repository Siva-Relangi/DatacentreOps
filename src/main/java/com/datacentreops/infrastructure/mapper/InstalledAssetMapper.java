package com.datacentreops.infrastructure.mapper;

import com.datacentreops.infrastructure.dto.*;
import com.datacentreops.infrastructure.entity.InstalledAsset;

public class InstalledAssetMapper {

    public static InstalledAsset toEntity(InstalledAssetRequestDTO dto) {

        InstalledAsset a = new InstalledAsset();

        a.setRackId(dto.getRackId());
        a.setCustomerId(dto.getCustomerId());
        a.setAssetType(dto.getAssetType());
        a.setMake(dto.getMake());
        a.setModel(dto.getModel());
        a.setSerialNumber(dto.getSerialNumber());
        a.setUnitPosition(dto.getUnitPosition());
        a.setUnitHeight(dto.getUnitHeight());
        a.setPowerDrawW(dto.getPowerDrawW());
        a.setInstalledDate(dto.getInstalledDate());

        return a;
    }

    public static InstalledAssetResponseDTO toDTO(InstalledAsset entity) {

        InstalledAssetResponseDTO dto = new InstalledAssetResponseDTO();

        dto.setAssetId(entity.getAssetId());
        dto.setRackId(entity.getRackId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setAssetType(entity.getAssetType());
        dto.setMake(entity.getMake());
        dto.setModel(entity.getModel());
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setUnitPosition(entity.getUnitPosition());
        dto.setUnitHeight(entity.getUnitHeight());
        dto.setPowerDrawW(entity.getPowerDrawW());
        dto.setInstalledDate(entity.getInstalledDate());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}