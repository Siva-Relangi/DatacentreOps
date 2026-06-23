package com.datacentreops.customer.dto;

import com.datacentreops.customer.entity.ContractType;
import com.datacentreops.customer.entity.ContractStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractResponseDTO {

    
    private Long contractId;
    private Long customerId;
    private ContractType contractType;
    private Integer allocatedRacks;
    private Double powerCommittedKW;
    private Double monthlyCost;
    private LocalDate contractStart;
    private LocalDate contractEnd;
    private String slaTier;
    private ContractStatus status;
}