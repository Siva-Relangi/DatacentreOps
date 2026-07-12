package com.datacentreops.connectivity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.connectivity.entity.CarrierPresence;
import com.datacentreops.connectivity.entity.CarrierStatus;
import com.datacentreops.connectivity.repository.CarrierPresenceRepository;
import com.datacentreops.connectivity.repository.CrossConnectRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.iam.entity.EntityType;

@Service
public class CarrierPresenceService {

    private final CarrierPresenceRepository repository;
    private final CrossConnectRepository crossConnectRepository;
    private final AuditLogRepository auditLogRepository;

    public CarrierPresenceService(CarrierPresenceRepository repository, CrossConnectRepository   crossConnectRepository, AuditLogRepository auditLogRepository) {
        this.repository = repository;
        this.crossConnectRepository = crossConnectRepository;
        this.auditLogRepository = auditLogRepository;
    }

    // CREATE
    public CarrierPresence create(CarrierPresence entity) {

        if (repository.existsByCarrierNameAndDataCentreId(entity.getCarrierName(), entity.getDataCentreId())) {

            throw new IllegalArgumentException(
                    "Carrier already exists in this Data Centre");
        }
        CarrierPresence saved= repository.save(entity);

        AuditLog audit = new AuditLog();
        audit.setAction(AuditAction.CREATE);
        audit.setEntityType(EntityType.CONNECTIVITY);
        audit.setRecordId(saved.getCarrierId());

        auditLogRepository.save(audit);

        return saved;
    }

    //  GET ALL
    public List<CarrierPresence> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public CarrierPresence findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CarrierPresence", id));
    }

    //  UPDATE
    public CarrierPresence update(Long id, CarrierPresence c) {

        CarrierPresence existing = findById(id);

        if (repository.existsByCarrierNameAndDataCentreIdAndCarrierIdNot(c.getCarrierName(), c.getDataCentreId(), id)) {

            throw new IllegalArgumentException(
                    "Carrier already exists in this Data Centre");
        }

        existing.setCarrierName(c.getCarrierName());
        existing.setServiceType(c.getServiceType());
        existing.setDataCentreId(c.getDataCentreId());
        existing.setMmrCabinetId(c.getMmrCabinetId());
        existing.setStatus(c.getStatus());

        AuditLog audit = new AuditLog();
        audit.setAction(AuditAction.UPDATE);
        audit.setEntityType(EntityType.CONNECTIVITY);
        audit.setRecordId(existing.getCarrierId());
        auditLogRepository.save(audit);
        
        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        CarrierPresence carrier = findById(id);
        if (!crossConnectRepository.findByCarrierId(id).isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot delete Carrier. Cross Connections exist.");
        }
        AuditLog audit = new AuditLog();
        audit.setAction(AuditAction.DELETE);
        audit.setEntityType(EntityType.CONNECTIVITY);
        audit.setRecordId(carrier.getCarrierId());
        auditLogRepository.save(audit);
        repository.deleteById(id);
    }

    //  SEARCH
    public List<CarrierPresence> findByDataCentre(Long dataCentreId) {
        return repository.findByDataCentreId(dataCentreId);
    }

    public List<CarrierPresence> findByStatus(CarrierStatus status) {
        return repository.findByStatus(status);
    }
}