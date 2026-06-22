package com.datacentreops.iam.mapper;

import com.datacentreops.iam.dto.AuditLogResponseDTO;
import com.datacentreops.iam.entity.AuditLog;

public class AuditLogMapper {

    public static AuditLogResponseDTO toDTO(AuditLog entity) {

        AuditLogResponseDTO dto = new AuditLogResponseDTO();

        dto.setAuditId(entity.getAuditId());
        dto.setUserId(entity.getUserId());
        dto.setAction(entity.getAction());
        dto.setEntityType(entity.getEntityType());
        dto.setRecordId(entity.getRecordId());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }
}