package com.datacentreops.capacity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CapacityReservationRequestDTO {

    @NotNull(message = "Customer is required")
    private Long customerId;

    @NotNull(message = "Hall is required")
    private Long hallId;

    @Positive(message = "Reserved U must be greater than 0")
    private Integer reservedU;

    @Positive(message = "Reserved Power must be greater than 0")
    private Double reservedPowerKW;

    private LocalDate reservationDate;

    private LocalDate expiryDate;
}