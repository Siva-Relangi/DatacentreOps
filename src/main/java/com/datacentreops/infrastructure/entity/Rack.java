package com.datacentreops.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rack")
@Getter
@Setter
@NoArgsConstructor
public class Rack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rack_id")
    private Long rackId;

    @Column(nullable = false)
    private Long hallId;

    @Column(nullable = false)
    private String rackLabel;

    @Column(nullable = false)
    private Integer totalU;

    private Integer usedU = 0;

    private Integer availableU;

    @Column(nullable = false)
    private Double maxPowerKW;

    private Double allocatedPowerKW = 0.0;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private RackStatus status = RackStatus.AVAILABLE;
}