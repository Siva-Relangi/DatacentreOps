package com.datacentreops.infrastructure.dto;

import com.datacentreops.infrastructure.entity.RackStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RackResponseDTO {

    private Long rackId;
    private Long hallId;
    private String rackLabel;
    private Integer totalU;
    private Integer usedU;
    private Integer availableU;
    private Double maxPowerKW;
    private Double allocatedPowerKW;
    private Long customerId;
    private RackStatus status;
}