package com.datacentreops.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RackRequestDTO {

    @NotNull(message = "Hall is required")
    private Long hallId;

    @NotBlank(message = "Rack Label is required")
    @Size(max = 20)
    private String rackLabel;

    @NotNull(message = "Total U is required")
    @Positive
    private Integer totalU;

    @NotNull(message = "Maximum Power is required")
    @Positive
    private Double maxPowerKW;
}