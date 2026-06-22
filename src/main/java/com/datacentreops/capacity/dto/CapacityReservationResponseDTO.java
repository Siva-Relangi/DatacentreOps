package com.datacentreops.capacity.dto;

import com.datacentreops.capacity.entity.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CapacityReservationResponseDTO {

    private Long reservationId;
    private Long customerId;
    private Long hallId;
    private Integer reservedU;
    private Double reservedPowerKW;
    private LocalDate reservationDate;
    private LocalDate expiryDate;
    private ReservationStatus status;
}