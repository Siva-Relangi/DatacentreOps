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

    @NotNull
    private Long hallId;

    private String rackLabel;

    @PositiveOrZero
    private Integer totalU;

    @PositiveOrZero
    private Integer usedU;

    @PositiveOrZero
    private Integer availableU;

    @PositiveOrZero
    private Double maxPowerKW;

    @PositiveOrZero
    private Double allocatedPowerKW;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private RackStatus status = RackStatus.AVAILABLE;
}