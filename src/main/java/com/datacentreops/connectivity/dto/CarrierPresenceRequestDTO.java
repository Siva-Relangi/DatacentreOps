package com.datacentreops.connectivity.dto;

import com.datacentreops.connectivity.entity.ServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrierPresenceRequestDTO {

    private String carrierName;
    private ServiceType serviceType;
    private Long dataCentreId;
    private String mmrCabinetId;
}