package com.datacentreops.workorder.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.iam.repository.UserRepository;
import com.datacentreops.workorder.entity.WorkOrderNote;
import com.datacentreops.workorder.repository.WorkOrderNoteRepository;
import com.datacentreops.workorder.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkOrderNoteService {

    private final WorkOrderNoteRepository repository;
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    public WorkOrderNoteService(
            WorkOrderNoteRepository repository,
            WorkOrderRepository workOrderRepository,
            UserRepository userRepository) {

        this.repository = repository;
        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
    }

    //  CREATE
    public WorkOrderNote create(WorkOrderNote note) {

        validate(note);
        return repository.save(note);
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