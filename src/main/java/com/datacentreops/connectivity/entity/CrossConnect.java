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

    @Column(nullable = false)
    private Long customerId;

    private Long rackId;

    private Long assetId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;

    @Column(nullable = false)
    private String portA;

    @Column(nullable = false)
    private String portZ;

    @Column(nullable = false)
    private String bandwidth;

    private Long carrierId;

    private LocalDate orderDate;

    private LocalDate provisionedDate;

    @Column(nullable = false)
    private Double monthlyCost;

    @Enumerated(EnumType.STRING)
    private CrossConnectStatus status = CrossConnectStatus.ORDERED;
}