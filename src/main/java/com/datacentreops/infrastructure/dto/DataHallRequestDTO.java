package com.datacentreops.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataHallRequestDTO {

    private Long dataCentreId;
    private String hallName;
    private Integer totalRacks;
    private Double totalPowerKW;
    private Double coolingCapacityKW;
    private String tierLevel;
}