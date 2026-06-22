package com.datacentreops.capacity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CapacityReservationRequestDTO {

    private Long customerId;
    private Long hallId;
    private Integer reservedU;
    private Double reservedPowerKW;
    private LocalDate reservationDate;
    private LocalDate expiryDate;
}