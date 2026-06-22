package com.datacentreops.iam.dto;

import com.datacentreops.iam.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogResponseDTO {

    private Long auditId;
    private Long userId;
    private AuditAction action;
    private EntityType entityType;
    private Long recordId;
    private LocalDateTime timestamp;
}