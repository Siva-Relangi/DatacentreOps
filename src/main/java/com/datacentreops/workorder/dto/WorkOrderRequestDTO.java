package com.datacentreops.workorder.dto;

import com.datacentreops.workorder.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderRequestDTO {

    private Long customerId;
    private Long rackId;
    private Long assetId;
    private WorkOrderType requestType;
    private String description;
    private WorkOrderPriority priority;
    private Long requestedById;
}