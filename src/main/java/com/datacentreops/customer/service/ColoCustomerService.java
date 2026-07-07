package com.datacentreops.customer.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.entity.ColoCustomer;
import com.datacentreops.customer.entity.CustomerStatus;
import com.datacentreops.customer.repository.ColoContractRepository;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ColoCustomerService {

    private final ColoCustomerRepository repository;
    private final ColoContractRepository contractRepository;

    public ColoCustomerService(ColoCustomerRepository repository,
                               ColoContractRepository contractRepository) {
        this.repository = repository;
        this.contractRepository = contractRepository;
    }

    //  CREATE
    public ColoCustomer create(ColoCustomer entity) {

        if(repository.existsByCompanyName(entity.getCompanyName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Company already exists");
        }
        return repository.save(entity);
    }

    //  GET ALL
    public List<ColoCustomer> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public ColoCustomer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ColoCustomer", id));
    }

    //  UPDATE
    public ColoCustomer update(Long id, ColoCustomer entity) {
        ColoCustomer existing = findById(id);

        if(!existing.getCompanyName().equals(entity.getCompanyName()) && repository.existsByCompanyName(entity.getCompanyName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Company already exists");
        }

        existing.setCompanyName(entity.getCompanyName());
        existing.setIndustrySegment(entity.getIndustrySegment());
        existing.setContactPerson(entity.getContactPerson());
        existing.setAccountManagerId(entity.getAccountManagerId());
        existing.setKycStatus(entity.getKycStatus());
        existing.setStatus(entity.getStatus());

        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        if (!contractRepository.findByCustomerId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete customer " + id + ": it still has contracts");
        }

        findById(id);
        repository.deleteById(id);
    }

    //  SEARCH
    public List<ColoCustomer> findByStatus(CustomerStatus status) {
        return repository.findByStatus(status);
    }

    public List<ColoCustomer> findByAccountManager(Long managerId) {
        return repository.findByAccountManagerId(managerId);
    }
}