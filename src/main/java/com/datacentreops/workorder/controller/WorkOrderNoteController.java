package com.datacentreops.workorder.controller;

import com.datacentreops.workorder.dto.*;
import com.datacentreops.workorder.entity.WorkOrderNote;
import com.datacentreops.workorder.mapper.WorkOrderNoteMapper;
import com.datacentreops.workorder.service.WorkOrderNoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-order-notes")
public class WorkOrderNoteController {

    private final WorkOrderNoteService service;

    public WorkOrderNoteController(WorkOrderNoteService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public WorkOrderNoteResponseDTO create(@RequestBody WorkOrderNoteRequestDTO dto) {

        return WorkOrderNoteMapper.toDTO(
                service.create(WorkOrderNoteMapper.toEntity(dto))
        );
    }

    // GET ALL
    @GetMapping
    public List<WorkOrderNoteResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(WorkOrderNoteMapper::toDTO)
                .toList();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public WorkOrderNoteResponseDTO getById(@PathVariable Long id) {

        return WorkOrderNoteMapper.toDTO(service.findById(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // FIND NOTES BY WORK ORDER
    @GetMapping("/by-work-order/{workOrderId}")
    public List<WorkOrderNoteResponseDTO> byWorkOrder(@PathVariable Long workOrderId) {

        return service.findByWorkOrder(workOrderId)
                .stream()
                .map(WorkOrderNoteMapper::toDTO)
                .toList();
    }
}