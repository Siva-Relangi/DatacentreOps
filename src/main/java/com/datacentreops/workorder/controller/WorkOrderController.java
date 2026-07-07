package com.datacentreops.workorder.controller;

import com.datacentreops.workorder.dto.WorkOrderResponseDTO;
import com.datacentreops.workorder.dto.WorkOrderRequestDTO;
import com.datacentreops.workorder.entity.WorkOrder;
import com.datacentreops.workorder.entity.WorkOrderStatus;
import com.datacentreops.workorder.mapper.WorkOrderMapper;
import com.datacentreops.workorder.service.WorkOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderService service;

    public WorkOrderController(WorkOrderService service) {
        this.service = service;
    }

    //  CREATE
    @PostMapping
    public WorkOrderResponseDTO create(@Valid @RequestBody WorkOrderRequestDTO dto) {

        WorkOrder entity = WorkOrderMapper.toEntity(dto);
        WorkOrder saved = service.create(entity);

        return WorkOrderMapper.toDTO(saved);
    }

    //  GET ALL
    @GetMapping
    public List<WorkOrderResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(WorkOrderMapper::toDTO)
                .toList();
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public WorkOrderResponseDTO getById(@PathVariable Long id) {

        return WorkOrderMapper.toDTO(
                service.findById(id)
        );
    }

    //  UPDATE
    @PutMapping("/{id}")
    public WorkOrderResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody WorkOrderRequestDTO dto) {

        WorkOrder entity = WorkOrderMapper.toEntity(dto);
        WorkOrder updated = service.update(id, entity);

        return WorkOrderMapper.toDTO(updated);
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    //  SEARCH
    @GetMapping("/search")
    public List<WorkOrderResponseDTO> search(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long engineerId,
            @RequestParam(required = false) WorkOrderStatus status) {

        List<WorkOrder> list;

        if (customerId != null) {
            list = service.findByCustomer(customerId);
        } else if (engineerId != null) {
            list = service.findByEngineer(engineerId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(WorkOrderMapper::toDTO)
                .toList();
    }

    // ASSIGN ENGINEER
    @PatchMapping("/{id}/assign")
    public WorkOrderResponseDTO assign(
            @PathVariable Long id,
            @RequestParam Long engineerId) {

        return WorkOrderMapper.toDTO(
                service.assign(id, engineerId)
        );
    }

    // CHANGE STATUS
    @PatchMapping("/{id}/status")
    public WorkOrderResponseDTO changeStatus(
            @PathVariable Long id,
            @RequestParam WorkOrderStatus status) {

        return WorkOrderMapper.toDTO(
                service.changeStatus(id, status)
        );
    }
}
