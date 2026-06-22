package com.datacentreops.workorder.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.iam.repository.UserRepository;
import com.datacentreops.infrastructure.repository.InstalledAssetRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import com.datacentreops.workorder.entity.WorkOrder;
import com.datacentreops.workorder.entity.WorkOrderStatus;
import com.datacentreops.workorder.repository.WorkOrderNoteRepository;
import com.datacentreops.workorder.repository.WorkOrderRepository;
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

    public WorkOrderService(
            WorkOrderRepository repository,
            ColoCustomerRepository customerRepository,
            RackRepository rackRepository,
            InstalledAssetRepository assetRepository,
            UserRepository userRepository,
            WorkOrderNoteRepository noteRepository) {

        this.repository = repository;
        this.customerRepository = customerRepository;
        this.rackRepository = rackRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
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

        if (w.getRackId() != null &&
                !rackRepository.existsById(w.getRackId())) {
            throw new ResourceNotFoundException("Rack", w.getRackId());
        }

        if (w.getAssetId() != null) {
            assetRepository.findById(w.getAssetId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset", w.getAssetId()));
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

        WorkOrder w = findById(id);

        w.setAssignedEngineerId(engineerId);
        w.setStatus(WorkOrderStatus.ASSIGNED);

        return repository.save(w);
    }

    //  CHANGE STATUS
    public WorkOrder changeStatus(Long id, WorkOrderStatus status) {

        WorkOrder w = findById(id);

        w.setStatus(status);

        if (status == WorkOrderStatus.COMPLETED) {
            w.setCompletionDate(LocalDateTime.now());
        }

        return repository.save(w);
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