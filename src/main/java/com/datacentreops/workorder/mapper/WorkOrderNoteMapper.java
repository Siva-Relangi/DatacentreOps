package com.datacentreops.workorder.mapper;

import com.datacentreops.workorder.dto.*;
import com.datacentreops.workorder.entity.WorkOrderNote;

public class WorkOrderNoteMapper {

    public static WorkOrderNote toEntity(WorkOrderNoteRequestDTO dto) {

        WorkOrderNote n = new WorkOrderNote();

        n.setWorkOrderId(dto.getWorkOrderId());
        n.setAuthorId(dto.getAuthorId());
        n.setNoteText(dto.getNoteText());
        n.setNoteType(dto.getNoteType());

        return n;
    }

    public static WorkOrderNoteResponseDTO toDTO(WorkOrderNote entity) {

        WorkOrderNoteResponseDTO dto = new WorkOrderNoteResponseDTO();

        dto.setNoteId(entity.getNoteId());
        dto.setWorkOrderId(entity.getWorkOrderId());
        dto.setAuthorId(entity.getAuthorId());
        dto.setNoteText(entity.getNoteText());
        dto.setNoteType(entity.getNoteType());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}