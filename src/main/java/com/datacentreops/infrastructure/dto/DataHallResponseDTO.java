package com.datacentreops.infrastructure.dto;

import com.datacentreops.infrastructure.entity.HallStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataHallResponseDTO {

    private Long hallId;
    private Long dataCentreId;
    private String hallName;
    private Integer totalRacks;
    private Double totalPowerKW;
    private Double coolingCapacityKW;
    private String tierLevel;
    private HallStatus status;
}