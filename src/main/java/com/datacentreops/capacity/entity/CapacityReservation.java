package com.datacentreops.capacity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "capacity_reservation")
@Getter
@Setter
@NoArgsConstructor
public class CapacityReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @NotNull
    private Long customerId;

    @NotNull
    private Long hallId;

    @PositiveOrZero
    private Integer reservedU;

    @PositiveOrZero
    private Double reservedPowerKW;

    private LocalDate reservationDate = LocalDate.now();

    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.ACTIVE;
}