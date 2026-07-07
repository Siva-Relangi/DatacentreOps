package com.datacentreops.capacity.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CapacitySnapshotRequestDTO {

    @NotNull(message = "Hall is required")
    private Long hallId;

    private LocalDate snapshotDate;

    @Positive
    private Integer totalRacks;

    @PositiveOrZero
    private Integer allocatedRacks;

    @Positive
    private Double totalPowerKW;

    @PositiveOrZero
    private Double allocatedPowerKW;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double coolingUsagePercent;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double spaceUtilisationPercent;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double powerUtilisationPercent;
}