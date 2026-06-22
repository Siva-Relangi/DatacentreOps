package com.datacentreops.capacity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "capacity_snapshot")
@Getter
@Setter
@NoArgsConstructor
public class CapacitySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snapshot_id")
    private Long snapshotId;

    @NotNull
    private Long hallId;

    private LocalDate snapshotDate = LocalDate.now();

    private Integer totalRacks;

    private Integer allocatedRacks;

    private Double totalPowerKW;

    private Double allocatedPowerKW;

    private Double coolingUsagePercent;

    private Double spaceUtilisationPercent;

    private Double powerUtilisationPercent;

    @Enumerated(EnumType.STRING)
    private SnapshotStatus status = SnapshotStatus.CURRENT;
}