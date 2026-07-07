package com.datacentreops.connectivity.dto;

import com.datacentreops.connectivity.entity.ServiceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrierPresenceRequestDTO {

    @NotBlank(message = "Carrier Name is required")
    private String carrierName;

    @NotNull(message = "Service Type is required")
    private ServiceType serviceType;

    @NotNull(message = "Data Centre is required")
    private Long dataCentreId;
    private String mmrCabinetId;
}