package com.datacentreops.workorder.dto;

import com.datacentreops.workorder.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkOrderResponseDTO {

    private Long workOrderId;
    private Long customerId;
    private Long rackId;
    private Long assetId;
    private Long incidentId;
    private WorkOrderType requestType;
    private String description;
    private WorkOrderPriority priority;
    private Long requestedById;
    private Long assignedEngineerId;
    private LocalDateTime requestedDate;
    private LocalDateTime completionDate;
    private WorkOrderStatus status;
}