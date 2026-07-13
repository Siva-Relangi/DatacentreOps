package com.datacentreops.workorder.mapper;

import com.datacentreops.workorder.dto.*;
import com.datacentreops.workorder.entity.WorkOrder;

public class WorkOrderMapper {

    public static WorkOrder toEntity(WorkOrderRequestDTO dto) {

        WorkOrder w = new WorkOrder();

        w.setCustomerId(dto.getCustomerId());
        w.setRackId(dto.getRackId());
        w.setAssetId(dto.getAssetId());
        w.setIncidentId(dto.getIncidentId());
        w.setRequestType(dto.getRequestType());
        w.setDescription(dto.getDescription());
        w.setPriority(dto.getPriority());
        w.setRequestedById(dto.getRequestedById());

        return w;
    }

    public static WorkOrderResponseDTO toDTO(WorkOrder entity) {

        WorkOrderResponseDTO dto = new WorkOrderResponseDTO();

        dto.setWorkOrderId(entity.getWorkOrderId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setRackId(entity.getRackId());
        dto.setAssetId(entity.getAssetId());
        dto.setRequestType(entity.getRequestType());
        dto.setIncidentId(entity.getIncidentId());
        dto.setDescription(entity.getDescription());
        dto.setPriority(entity.getPriority());
        dto.setRequestedById(entity.getRequestedById());
        dto.setAssignedEngineerId(entity.getAssignedEngineerId());
        dto.setRequestedDate(entity.getRequestedDate());
        dto.setCompletionDate(entity.getCompletionDate());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}