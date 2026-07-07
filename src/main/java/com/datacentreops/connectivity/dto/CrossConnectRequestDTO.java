package com.datacentreops.connectivity.dto;

import com.datacentreops.connectivity.entity.ConnectionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CrossConnectRequestDTO {

    @NotNull(message = "Customer is required")
    private Long customerId;

    private Long rackId;

    private Long assetId;

    @NotNull(message = "Connection Type is required")
    private ConnectionType connectionType;

    @NotNull(message = "Port A is required")
    private String portA;

    @NotNull(message = "Port Z is required")
    private String portZ;

    @NotNull(message = "Bandwidth is required")
    private String bandwidth;

    private Long carrierId;

    private LocalDate orderDate;

    private LocalDate provisionedDate;

    @Positive
    private Double monthlyCost;
}