package com.datacentreops.customer.controller;

import com.datacentreops.customer.dto.*;
import com.datacentreops.customer.entity.ColoContract;
import com.datacentreops.customer.entity.ContractStatus;
import com.datacentreops.customer.mapper.ContractMapper;
import com.datacentreops.customer.service.ColoContractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colocation/contracts")
public class ColoContractController {

    private final ColoContractService service;

    public ColoContractController(ColoContractService service) {
        this.service = service;
    }

    @PostMapping
    public ContractResponseDTO create(@RequestBody ContractRequestDTO dto) {
        ColoContract saved = service.create(ContractMapper.toEntity(dto));
        return ContractMapper.toDTO(saved);
    }

    @GetMapping
    public List<ContractResponseDTO> getAll() {
        return service.findAll()
                .stream()
                .map(ContractMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ContractResponseDTO getById(@PathVariable Long id) {
        return ContractMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public ContractResponseDTO update(@PathVariable Long id,
                                      @RequestBody ContractRequestDTO dto) {

        return ContractMapper.toDTO(
                service.update(id, ContractMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<ContractResponseDTO> search(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) ContractStatus status) {

        List<ColoContract> list;

        if (customerId != null) {
            list = service.findByCustomer(customerId);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(ContractMapper::toDTO)
                .toList();
    }
}