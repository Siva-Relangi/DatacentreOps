package com.datacentreops.workorder.repository;

import com.datacentreops.workorder.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    List<WorkOrder> findByCustomerId(Long customerId);

    List<WorkOrder> findByAssignedEngineerId(Long engineerId);

    List<WorkOrder> findByStatus(WorkOrderStatus status);
}