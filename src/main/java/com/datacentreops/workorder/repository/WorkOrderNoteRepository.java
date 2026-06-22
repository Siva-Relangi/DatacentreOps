package com.datacentreops.workorder.repository;

import com.datacentreops.workorder.entity.WorkOrderNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderNoteRepository extends JpaRepository<WorkOrderNote, Long> {
    List<WorkOrderNote> findByWorkOrderId(Long workOrderId);
}
