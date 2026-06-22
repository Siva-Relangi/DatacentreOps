package com.datacentreops.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InstalledAssetRequestDTO {

    private Long rackId;
    private Long customerId;
    private String assetType;
    private String make;
    private String model;
    private String serialNumber;
    private Integer uPosition;
    private Integer uHeight;
    private Double powerDrawW;
    private LocalDate installedDate;
}