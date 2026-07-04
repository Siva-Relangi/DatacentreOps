package com.datacentreops.infrastructure.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.entity.EntityType;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.infrastructure.entity.Rack;
import com.datacentreops.infrastructure.entity.RackStatus;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import com.datacentreops.infrastructure.repository.InstalledAssetRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RackService {

    private final RackRepository repository;
    private final DataHallRepository dataHallRepository;
    private final InstalledAssetRepository assetRepository;
    private final ColoCustomerRepository customerRepository;
    private final AuditLogRepository auditLogRepository;

    public RackService(
            RackRepository repository,
            DataHallRepository dataHallRepository,
            InstalledAssetRepository assetRepository,
            ColoCustomerRepository customerRepository,
            AuditLogRepository auditLogRepository) {

        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
        this.assetRepository = assetRepository;
        this.customerRepository = customerRepository;
        this.auditLogRepository = auditLogRepository;
    }

    //  CREATE
    public Rack create(Rack r) {
        validate(r);

        if(!dataHallRepository.existsById(r.getRackId())){
            throw new ResourceNotFoundException("DataHall", r.getHallId());
        }

        if(repository.existsByHallIdAndRackLabel(r.getHallId(), r.getRackLabel())){
            throw new IllegalArgumentException("Rack label already exists in this Hall");
        }

        r.setUsedU(0);
        r.setAvailableU(r.getTotalU());
        r.setAllocatedPowerKW(0.0);
        r.setStatus(RackStatus.AVAILABLE);

        // AuditLog
        AuditLog log = new AuditLog();
        log.setAction(AuditAction.CREATE);
        log.setEntityType(EntityType.RACK);
        log.setRecordId(r.getRackId());
        auditLogRepository.save(log);

        return repository.save(r);
    }

    // GET ALL
    public List<Rack> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public Rack findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rack", id));
    }

    //  UPDATE
    public Rack update(Long id, Rack r) {

        Rack existing = findById(id);

        existing.setHallId(r.getHallId());
        existing.setRackLabel(r.getRackLabel());
        existing.setTotalU(r.getTotalU());
        existing.setUsedU(r.getUsedU());
        existing.setAvailableU(r.getAvailableU());
        existing.setMaxPowerKW(r.getMaxPowerKW());
        existing.setAllocatedPowerKW(r.getAllocatedPowerKW());
        existing.setCustomerId(r.getCustomerId());
        existing.setStatus(r.getStatus());

        validate(existing);

        int usedU = existing.getUsedU() == null ? 0 : existing.getUsedU();
        if(r.getTotalU() <= usedU){
            throw new IllegalArgumentException("Total U cannot be less than current used U");
        }

        double allocatedPower = existing.getAllocatedPowerKW() == null ? 0 : existing.getAllocatedPowerKW();
        if(r.getMaxPowerKW() < allocatedPower){
            throw new IllegalArgumentException("Maximum Power cannot be less than allocated power");
        }

        if(repository.existsByHallIdAndRackLabelAndRackIdNot(r.getHallId(), r.getRackLabel(), id)){
            throw new IllegalArgumentException("Rack label already exists in this Hall");
        }

        // AuditLog
        AuditLog log = new AuditLog();
        log.setAction(AuditAction.UPDATE);
        log.setEntityType(EntityType.RACK);
        log.setRecordId(existing.getRackId());
        auditLogRepository.save(log);

        return repository.save(existing);
    }

    //  DELETE (BUSINESS RULE )
    public void delete(Long id) {

        if (!assetRepository.findByRackId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete rack " + id + ": it still has installed assets");
        }

        Rack rack = findById(id);
        if(rack.getCustomerId() != null){
            throw new IllegalArgumentException("Cannot delete allocated rack");
        }

        AuditLog log = new AuditLog();
        log.setAction(AuditAction.DELETE);
        log.setEntityType(EntityType.RACK);
        log.setRecordId(id);
        auditLogRepository.save(log);
        repository.deleteById(id);
    }

    //  VALIDATION
    private void validate(Rack r) {

        if(r.getRackLabel() == null || r.getRackLabel().isBlank()){
            throw new IllegalArgumentException("Rack Label is required");
        }

        if(r.getTotalU() == null || r.getTotalU() <= 0){
            throw new IllegalArgumentException("Total U must be greater than 0");
        }

        if(r.getMaxPowerKW() == null || r.getMaxPowerKW() <= 0){
            throw new IllegalArgumentException("Maximum Power must be greater than 0");
        }

        if (r.getCustomerId() != null &&
                !customerRepository.existsById(r.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", r.getCustomerId());
        }

    }

    //  SEARCH
    public List<Rack> findByHall(Long hallId) {
        return repository.findByHallId(hallId);
    }

    public List<Rack> findByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<Rack> findByStatus(RackStatus status) {
        return repository.findByStatus(status);
    }
}