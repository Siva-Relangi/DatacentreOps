package com.datacentreops.connectivity.dto;

import com.datacentreops.connectivity.entity.ConnectionType;
import com.datacentreops.connectivity.entity.CrossConnectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CrossConnectResponseDTO {

    private Long crossConnectId;
    private Long customerId;
    private Long rackId;
    private Long assetId;
    private ConnectionType connectionType;
    private String portA;
    private String portZ;
    private String bandwidth;
    private Long carrierId;
    private LocalDate orderDate;
    private LocalDate provisionedDate;
    private Double monthlyCost;
    private CrossConnectStatus status;
}