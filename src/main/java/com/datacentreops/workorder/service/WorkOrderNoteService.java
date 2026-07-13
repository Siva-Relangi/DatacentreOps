package com.datacentreops.workorder.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.iam.repository.UserRepository;
import com.datacentreops.workorder.entity.WorkOrder;
import com.datacentreops.workorder.entity.WorkOrderNote;
import com.datacentreops.workorder.entity.WorkOrderStatus;
import com.datacentreops.workorder.repository.WorkOrderNoteRepository;
import com.datacentreops.workorder.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;
import com.datacentreops.iam.entity.EntityType;

import java.util.List;

@Service
public class WorkOrderNoteService {

    private final WorkOrderNoteRepository repository;
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    public WorkOrderNoteService(
            WorkOrderNoteRepository repository,
            WorkOrderRepository workOrderRepository,
            UserRepository userRepository,
            AuditLogRepository auditLogRepository) {

        this.repository = repository;
        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
        this.auditLogRepository = auditLogRepository;
    }

    //  CREATE
    public WorkOrderNote create(WorkOrderNote note) {

        validate(note);

        WorkOrder workOrder = workOrderRepository.findById(note.getWorkOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Workorder", note.getWorkOrderId()));

        if(workOrder.getStatus() == WorkOrderStatus.ASSIGNED){
            workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
            workOrderRepository.save(workOrder);
        }

        WorkOrderNote saved = repository.save(note);


        AuditLog audit = new AuditLog();
        audit.setAction(AuditAction.CREATE);
        audit.setEntityType(EntityType.WORK_ORDER);
        audit.setRecordId(saved.getNoteId());

        auditLogRepository.save(audit);

        return saved;
    }

    //  GET ALL
    public List<WorkOrderNote> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public WorkOrderNote findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkOrderNote", id));
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        AuditLog audit = new AuditLog();
        audit.setAction(AuditAction.DELETE);
        audit.setEntityType(EntityType.WORK_ORDER);
        audit.setRecordId(id);

        auditLogRepository.save(audit);
        repository.deleteById(id);
    }

    //  VALIDATION
    private void validate(WorkOrderNote note) {

        if (!workOrderRepository.existsById(note.getWorkOrderId())) {
            throw new ResourceNotFoundException("WorkOrder", note.getWorkOrderId());
        }

        if (note.getAuthorId() != null &&
                !userRepository.existsById(note.getAuthorId())) {
            throw new ResourceNotFoundException("User", note.getAuthorId());
        }
    }

    //  FIND BY WORK ORDER
    public List<WorkOrderNote> findByWorkOrder(Long workOrderId) {
        return repository.findByWorkOrderId(workOrderId);
    }
}