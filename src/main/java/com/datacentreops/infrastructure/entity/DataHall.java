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

    private Long dataCentreId;

    @NotBlank
    private String hallName;

    @PositiveOrZero
    private Integer totalRacks;

    @PositiveOrZero
    private Double totalPowerKW;

    @PositiveOrZero
    private Double coolingCapacityKW;

    private String tierLevel;

    @Enumerated(EnumType.STRING)
    private HallStatus status = HallStatus.OPERATIONAL;
}
