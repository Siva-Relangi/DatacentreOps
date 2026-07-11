package com.datacentreops.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Getter
@Setter
public class InstalledAssetRequestDTO {

    @NotNull(message = "Rack is required")
    private Long rackId;

    private Long customerId;

    @NotBlank(message = "Asset Type is required")
    private String assetType;

    private String make;

    private String model;

    @NotBlank(message = "Serial Number is required")
    private String serialNumber;

    private Integer unitPosition;

    @Positive(message = "U Height must be greater than 0")

    private Integer unitHeight;

    @Positive(message = "Power Draw must be greater than 0")
    private Double powerDrawW;
    
    private LocalDate installedDate;
}