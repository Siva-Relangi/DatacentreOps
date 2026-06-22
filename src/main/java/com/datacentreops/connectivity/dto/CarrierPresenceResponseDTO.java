package com.datacentreops.connectivity.dto;

import com.datacentreops.connectivity.entity.CarrierStatus;
import com.datacentreops.connectivity.entity.ServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrierPresenceResponseDTO {

    private Long carrierId;

    private String carrierName;

    private ServiceType serviceType;

    private Long dataCentreId;

    private String mmrCabinetId;

    private CarrierStatus status;
}