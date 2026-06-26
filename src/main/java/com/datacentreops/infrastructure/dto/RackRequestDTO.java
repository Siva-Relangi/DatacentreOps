package com.datacentreops.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RackRequestDTO {

    private Long hallId;
    private String rackLabel;
    private Integer totalU;
    private Double maxPowerKW;
}