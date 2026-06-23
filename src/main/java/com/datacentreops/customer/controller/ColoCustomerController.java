package com.datacentreops.customer.controller;

import com.datacentreops.customer.dto.CustomerRequestDTO;
import com.datacentreops.customer.dto.CustomerResponseDTO;
import com.datacentreops.customer.entity.ColoCustomer;
import com.datacentreops.customer.entity.CustomerStatus;
import com.datacentreops.customer.mapper.CustomerMapper;
import com.datacentreops.customer.service.ColoCustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colocation/customers")
public class ColoCustomerController {

    private final ColoCustomerService service;

    public ColoCustomerController(ColoCustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerResponseDTO create(@Valid @RequestBody CustomerRequestDTO dto) {
        ColoCustomer saved = service.create(CustomerMapper.toEntity(dto));
        return CustomerMapper.toDTO(saved);
    }

    @GetMapping
    public List<CustomerResponseDTO> getAll() {
        return service.findAll()
                .stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getById(@PathVariable Long id) {
        return CustomerMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO update(@PathVariable Long id,
                                      @Valid @RequestBody CustomerRequestDTO dto) {

        ColoCustomer updated = service.update(id, CustomerMapper.toEntity(dto));
        return CustomerMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<CustomerResponseDTO> search(
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(required = false) Long accountManagerId) {

        List<ColoCustomer> list;

        if (status != null) {
            list = service.findByStatus(status);
        } else if (accountManagerId != null) {
            list = service.findByAccountManager(accountManagerId);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }
}