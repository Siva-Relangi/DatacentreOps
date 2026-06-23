package com.datacentreops.customer.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.entity.ColoContract;
import com.datacentreops.customer.entity.ContractStatus;
import com.datacentreops.customer.repository.ColoContractRepository;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColoContractService {

    private final ColoContractRepository repository;
    private final ColoCustomerRepository customerRepository;

    public ColoContractService(ColoContractRepository repository,
                               ColoCustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    //  CREATE
    public ColoContract create(ColoContract entity) {

        if (!customerRepository.existsById(entity.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", entity.getCustomerId());
        }
        validate(entity);
        return repository.save(entity);
    }

    private void validate(ColoContract entity) {

        if(entity.getAllocatedRacks() == null || entity.getAllocatedRacks() <= 0) {
            throw new IllegalArgumentException("Allocated racks must be greater than 0");
        }

        if(entity.getPowerCommittedKW() == null || entity.getPowerCommittedKW() <= 0) {
            throw new IllegalArgumentException("Power committed must be greater than 0");
        }

        if(entity.getMonthlyCost() == null || entity.getMonthlyCost() <= 0) {
            throw new IllegalArgumentException("Monthly cost must be greater than 0");
        }

        if(entity.getContractStart() != null && entity.getContractEnd() != null && entity.getContractEnd().isBefore(entity.getContractStart())) {
            throw new IllegalArgumentException("Contract end date cannot be before start date");
        }
    }
    //  GET ALL
    public List<ColoContract> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public ColoContract findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", id));
    }

    //  UPDATE
    public ColoContract update(Long id, ColoContract entity) {

        ColoContract existing = findById(id);

        existing.setContractType(entity.getContractType());
        existing.setAllocatedRacks(entity.getAllocatedRacks());
        existing.setPowerCommittedKW(entity.getPowerCommittedKW());
        existing.setMonthlyCost(entity.getMonthlyCost());
        existing.setContractStart(entity.getContractStart());
        existing.setContractEnd(entity.getContractEnd());  
        existing.setSlaTier(entity.getSlaTier());

        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public List<ColoContract> findByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<ColoContract> findByStatus(ContractStatus status) {
        return repository.findByStatus(status);
    }
}