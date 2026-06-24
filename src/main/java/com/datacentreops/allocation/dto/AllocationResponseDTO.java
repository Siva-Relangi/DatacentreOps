package com.datacentreops.allocation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AllocationResponseDTO{
    
    private Long customerId;
    private Long contractId;
    private Integer allocatedRacks;
    private Double allocatedPowerKW;
    private String message;
}