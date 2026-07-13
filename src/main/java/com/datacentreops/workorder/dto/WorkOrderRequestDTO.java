package com.datacentreops.workorder.dto;

import com.datacentreops.workorder.entity.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderRequestDTO {

    @NotNull(message = "Customer is required")
    private Long customerId;

    private Long rackId;

    private Long incidentId;

    private Long assetId;

    @NotNull(message = "Request Type is required")
    @Enumerated(EnumType.STRING)
    private WorkOrderType requestType;

    @NotBlank(message = "Description is required")
    private String description;

    private WorkOrderPriority priority;

    @NotNull(message = "Requested By is required")
    private Long requestedById;
}