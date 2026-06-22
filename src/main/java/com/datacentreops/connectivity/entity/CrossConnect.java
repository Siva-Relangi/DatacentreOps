package com.datacentreops.connectivity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cross_connect")
@Getter
@Setter
@NoArgsConstructor
public class CrossConnect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cross_connect_id")
    private Long crossConnectId;

    @NotNull
    private Long customerId;

    private Long rackId;

    private Long assetId;

    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;

    private String portA;

    private String portZ;

    private String bandwidth;

    private Long carrierId;

    private LocalDate orderDate;

    private LocalDate provisionedDate;

    private Double monthlyCost;

    @Enumerated(EnumType.STRING)
    private CrossConnectStatus status = CrossConnectStatus.ORDERED;
}