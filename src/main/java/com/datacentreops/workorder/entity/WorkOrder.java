package com.datacentreops.workorder.entity;

import com.datacentreops.iam.repository.AuditLogRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_order")
@Getter
@Setter
@NoArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workOrderId;

    @Column(nullable = false)
    private Long customerId;

    private Long rackId;

    private Long incidentId;

    private Long assetId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkOrderType requestType;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private WorkOrderPriority priority = WorkOrderPriority.STANDARD;

    @Column(nullable = false)
    private Long requestedById;

    private Long assignedEngineerId;

    private LocalDateTime requestedDate = LocalDateTime.now();

    private LocalDateTime completionDate;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status = WorkOrderStatus.OPEN;
}