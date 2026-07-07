package com.datacentreops.workorder.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.iam.repository.UserRepository;
import com.datacentreops.infrastructure.entity.InstalledAsset;
import com.datacentreops.infrastructure.entity.Rack;
import com.datacentreops.infrastructure.repository.InstalledAssetRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import com.datacentreops.notification.entity.Notification;
import com.datacentreops.notification.repository.NotificationRepository;
import com.datacentreops.workorder.entity.WorkOrder;
import com.datacentreops.workorder.entity.WorkOrderStatus;
import com.datacentreops.workorder.repository.WorkOrderNoteRepository;
import com.datacentreops.workorder.repository.WorkOrderRepository;
import com.datacentreops.iam.entity.EntityType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkOrderService {

    private final WorkOrderRepository repository;
    private final ColoCustomerRepository customerRepository;
    private final RackRepository rackRepository;
    private final InstalledAssetRepository assetRepository;
    private final UserRepository userRepository;
    private final WorkOrderNoteRepository noteRepository;
    private final NotificationRepository notificationRepository;
    private final AuditLogRepository auditLogRepository;

    public WorkOrderService(
            WorkOrderRepository repository,
            ColoCustomerRepository customerRepository,
            RackRepository rackRepository,
            InstalledAssetRepository assetRepository,
            UserRepository userRepository,
            WorkOrderNoteRepository noteRepository,
            NotificationRepository notificationRepository,
            AuditLogRepository auditLogRepository) {

        this.repository = repository;
        this.customerRepository = customerRepository;
        this.rackRepository = rackRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.notificationRepository = notificationRepository;
        this.auditLogRepository = auditLogRepository;
    }

    //  CREATE
    public WorkOrder create(WorkOrder w) {
        validate(w);
        return repository.save(w);
    }

    //  GET ALL
    public List<WorkOrder> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public WorkOrder findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkOrder", id));
    }

    //  UPDATE
    public WorkOrder update(Long id, WorkOrder w) {

        WorkOrder existing = findById(id);

        existing.setCustomerId(w.getCustomerId());
        existing.setRackId(w.getRackId());
        existing.setAssetId(w.getAssetId());
        existing.setRequestType(w.getRequestType());
        existing.setDescription(w.getDescription());
        existing.setPriority(w.getPriority());
        existing.setRequestedById(w.getRequestedById());

        validate(existing);
        return repository.save(existing);
    }

    //  DELETE (BUSINESS RULE)
    public void delete(Long id) {

        if (!noteRepository.findByWorkOrderId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete work order " + id + ": it still has notes");
        }

        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION
    private void validate(WorkOrder w) {

        if (!customerRepository.existsById(w.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", w.getCustomerId());
        }

        if (w.getRackId() != null ) {

            Rack rack = rackRepository.findById(w.getRackId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rack", w.getRackId()));
            if(rack.getCustomerId() != null && !rack.getCustomerId().equals(w.getCustomerId())){
                throw new IllegalArgumentException("Rack is not allocated to this customer");
            }
        }

        if (w.getAssetId() != null) {
            InstalledAsset asset = assetRepository.findById(w.getAssetId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset", w.getAssetId()));

            if(!asset.getRackId().equals(w.getRackId())){
                throw new IllegalArgumentException("Asset does not belong to selected rack");
            }
        }

        if (w.getRequestedById() != null &&
                !userRepository.existsById(w.getRequestedById())) {
            throw new ResourceNotFoundException("User", w.getRequestedById());
        }

    }

    //  ASSIGN ENGINEER
    public WorkOrder assign(Long id, Long engineerId) {
        if (!userRepository.existsById(engineerId)) {
            throw new ResourceNotFoundException("User", engineerId);
        }
        WorkOrder workOrder = findById(id);
        workOrder.setAssignedEngineerId(engineerId);
        workOrder.setStatus(WorkOrderStatus.ASSIGNED);
        WorkOrder saved = repository.save(workOrder);
        Notification notification = new Notification();
        notification.setUserId(engineerId);
        notification.setMessage("Work Order " + saved.getWorkOrderId() + " assigned to you");
        notificationRepository.save(notification);

        AuditLog audit = new AuditLog();
        audit.setUserId(engineerId);
        audit.setAction(AuditAction.ASSIGN);
        audit.setEntityType(EntityType.WORK_ORDER);
        audit.setRecordId(saved.getWorkOrderId());
        auditLogRepository.save(audit);

        return saved;
    }
 

    //  CHANGE STATUS
    public WorkOrder changeStatus(Long id, WorkOrderStatus status) {
        WorkOrder workOrder = findById(id);
        workOrder.setStatus(status);
        if (status == WorkOrderStatus.COMPLETED) {
            workOrder.setCompletionDate(LocalDateTime.now());
        }
        
        WorkOrder saved = repository.save(workOrder);
        Notification notification = new Notification();
        notification.setUserId(saved.getRequestedById());
        notification.setMessage("Work Order " + saved.getWorkOrderId() + " changed to " + status);
        notificationRepository.save(notification);

        AuditLog audit = new AuditLog();
        audit.setUserId(saved.getRequestedById());
        audit.setAction(AuditAction.STATUS_CHANGE);
        audit.setEntityType(EntityType.WORK_ORDER);
        audit.setRecordId(saved.getWorkOrderId());
        auditLogRepository.save(audit);

        return saved;
    }
 

    //  SEARCH
    public List<WorkOrder> findByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<WorkOrder> findByEngineer(Long engineerId) {
        return repository.findByAssignedEngineerId(engineerId);
    }

    public List<WorkOrder> findByStatus(WorkOrderStatus status) {
        return repository.findByStatus(status);
    }
}