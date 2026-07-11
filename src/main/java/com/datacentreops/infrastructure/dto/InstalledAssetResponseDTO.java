package com.datacentreops.infrastructure.dto;

import com.datacentreops.infrastructure.entity.AssetStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InstalledAssetResponseDTO {

    private Long assetId;
    private Long rackId;
    private Long customerId;
    private String assetType;
    private String make;
    private String model;
    private String serialNumber;
    private Integer unitPosition;
    private Integer unitHeight;
    private Double powerDrawW;
    private LocalDate installedDate;
    private AssetStatus status;
}