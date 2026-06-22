package com.datacentreops.capacity.dto;

import com.datacentreops.capacity.entity.SnapshotStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CapacitySnapshotResponseDTO {

    private Long snapshotId;
    private Long hallId;
    private LocalDate snapshotDate;
    private Integer totalRacks;
    private Integer allocatedRacks;
    private Double totalPowerKW;
    private Double allocatedPowerKW;
    private Double coolingUsagePercent;
    private Double spaceUtilisationPercent;
    private Double powerUtilisationPercent;
    private SnapshotStatus status;
}