package com.datacentreops.customer.dto;

import com.datacentreops.customer.entity.ContractType;
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
    private String status;
}