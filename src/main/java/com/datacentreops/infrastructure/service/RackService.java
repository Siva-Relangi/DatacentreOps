package com.datacentreops.infrastructure.service;

import com.datacentreops.common.ResourceNotFoundException;
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
    private final AuditLogRepository auditLogRepository;

    public RackService(
            RackRepository repository,
            DataHallRepository dataHallRepository,
            InstalledAssetRepository assetRepository,
            AuditLogRepository auditLogRepository) {

        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
        this.assetRepository = assetRepository;
        this.auditLogRepository = auditLogRepository;
    }

    //  CREATE
    public Rack create(Rack r) {

        if(!dataHallRepository.existsById(r.getHallId())){
            throw new ResourceNotFoundException("DataHall", r.getHallId());
        }

        if(repository.existsByHallIdAndRackLabel(r.getHallId(), r.getRackLabel())){
            throw new IllegalArgumentException("Rack label already exists in this Hall");
        }

        r.setUsedU(0);
        r.setAvailableU(r.getTotalU());
        r.setAllocatedPowerKW(0.0);
        r.setStatus(RackStatus.AVAILABLE);

        Rack saved = repository.save(r);
        // AuditLog
        AuditLog log = new AuditLog();
        log.setAction(AuditAction.CREATE);
        log.setEntityType(EntityType.RACK);
        log.setRecordId(saved.getRackId());
        auditLogRepository.save(log);

        return saved;
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

        if (!dataHallRepository.existsById(r.getHallId())) {
            throw new ResourceNotFoundException("DataHall", r.getHallId());
        }

        existing.setHallId(r.getHallId());
        existing.setRackLabel(r.getRackLabel());
        existing.setTotalU(r.getTotalU());
        existing.setMaxPowerKW(r.getMaxPowerKW());

        int usedU = existing.getUsedU() == null ? 0 : existing.getUsedU();
        if(r.getTotalU() < usedU){
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

    // Status Change
    public Rack changeStatus(Long id, RackStatus status){
        Rack rack = findById(id);

        if(rack.getCustomerId() != null){
            throw new IllegalArgumentException("Cannot change status of an allocated rack");
        }
        rack.setStatus(status);

        Rack saved = repository.save(rack);

        AuditLog log = new AuditLog();
        log.setAction(AuditAction.STATUS_CHANGE);
        log.setEntityType(EntityType.RACK);
        log.setRecordId(saved.getRackId());

        auditLogRepository.save(log);

        return saved;
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