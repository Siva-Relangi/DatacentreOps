package com.datacentreops.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "data_hall")
@Getter
@Setter
@NoArgsConstructor
public class DataHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Long hallId;

    @Column(nullable = false)
    private Long dataCentreId;

    @Column(nullable = false, unique = true)
    private String hallName;

    @Column(nullable = false)
    private Integer totalRacks;

    @Column(nullable = false)
    private Double totalPowerKW;

    @Column(nullable = false)
    private Double coolingCapacityKW;

    @Column(nullable = false)
    private String tierLevel;

    @Enumerated(EnumType.STRING)
    private HallStatus status = HallStatus.OPERATIONAL;
}
