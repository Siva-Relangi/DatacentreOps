package com.datacentreops.customer.dto;

import com.datacentreops.customer.entity.ContractType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractRequestDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private ContractType contractType;

    @Positive
    private Integer allocatedRacks;

    @Positive
    private Double powerCommittedKW;

    @Positive
    private Double monthlyCost;

    private LocalDate contractStart;

    private LocalDate contractEnd;

    @NotBlank(message = "SLA Tier is required")
    private String slaTier;
}