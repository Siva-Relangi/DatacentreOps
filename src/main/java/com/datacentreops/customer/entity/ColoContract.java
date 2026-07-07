package com.datacentreops.customer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Entity
@Table(name = "colo_contract")
@Getter
@Setter
@NoArgsConstructor
public class ColoContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    @Column(nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column(nullable = false)
    private Integer allocatedRacks;

    @Column(nullable = false)
    private Double powerCommittedKW;

    private LocalDate contractStart;

    private LocalDate contractEnd;

    private String slaTier;

    @Column(nullable = false)
    private Double monthlyCost;

    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.ACTIVE;
}