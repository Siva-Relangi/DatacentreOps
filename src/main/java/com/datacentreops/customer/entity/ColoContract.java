package com.datacentreops.customer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "colo_contract")
@Getter
@Setter
@NoArgsConstructor
public class ColoContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    private Integer allocatedRacks;

    private Double powerCommittedKW;

    private LocalDate contractStart;

    private LocalDate contractEnd;

    private String slaTier;

    private Double monthlyCost;

    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.ACTIVE;
}