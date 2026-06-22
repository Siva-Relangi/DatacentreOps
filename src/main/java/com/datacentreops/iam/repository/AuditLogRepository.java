package com.datacentreops.iam.repository;

import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByEntityType(EntityType entityType);

    List<AuditLog> findByEntityTypeAndRecordId(EntityType entityType, Long recordId);
}