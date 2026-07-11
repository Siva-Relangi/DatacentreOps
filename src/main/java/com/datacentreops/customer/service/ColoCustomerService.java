package com.datacentreops.customer.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.entity.ColoCustomer;
import com.datacentreops.customer.entity.CustomerStatus;
import com.datacentreops.customer.repository.ColoContractRepository;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.entity.EntityType;
import com.datacentreops.iam.repository.AuditLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ColoCustomerService {

    private final ColoCustomerRepository repository;
    private final ColoContractRepository contractRepository;
    private final AuditLogRepository auditLogRepository;

    public ColoCustomerService(ColoCustomerRepository repository,
                               ColoContractRepository contractRepository,
                               AuditLogRepository auditLogRepository) {
        this.repository = repository;
        this.contractRepository = contractRepository;
        this.auditLogRepository = auditLogRepository;
    }

    //  CREATE
    public ColoCustomer create(ColoCustomer entity) {

        if(repository.existsByCompanyName(entity.getCompanyName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Company already exists");
        }

        // AuditLog
        ColoCustomer saved = repository.save(entity);
        AuditLog log = new AuditLog();
        log.setAction(AuditAction.CREATE);
        log.setEntityType(EntityType.CUSTOMER);
        log.setRecordId(saved.getCustomerId());
        auditLogRepository.save(log);

        return saved;
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

        AuditLog log = new AuditLog();
        log.setAction(AuditAction.UPDATE);
        log.setEntityType(EntityType.CUSTOMER);
        log.setRecordId(entity.getCustomerId());
        auditLogRepository.save(log);

        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        if (!contractRepository.findByCustomerId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete customer " + id + ": it still has contracts");
        }

        findById(id);
        AuditLog log = new AuditLog();
        log.setAction(AuditAction.DELETE);
        log.setEntityType(EntityType.CUSTOMER);
        log.setRecordId(id);
        auditLogRepository.save(log);

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