package com.datacentreops.capacity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CapacitySnapshotRequestDTO {

    private Long hallId;
    private LocalDate snapshotDate;
    private Integer totalRacks;
    private Integer allocatedRacks;
    private Double totalPowerKW;
    private Double allocatedPowerKW;
    private Double coolingUsagePercent;
    private Double spaceUtilisationPercent;
    private Double powerUtilisationPercent;
}