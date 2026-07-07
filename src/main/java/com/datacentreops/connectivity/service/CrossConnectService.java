package com.datacentreops.connectivity.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.connectivity.entity.CrossConnect;
import com.datacentreops.connectivity.entity.CrossConnectStatus;
import com.datacentreops.connectivity.repository.CarrierPresenceRepository;
import com.datacentreops.connectivity.repository.CrossConnectRepository;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.infrastructure.entity.InstalledAsset;
import com.datacentreops.infrastructure.repository.InstalledAssetRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import com.datacentreops.notification.repository.NotificationRepository;
import com.datacentreops.notification.entity.*;
import com.datacentreops.iam.entity.*;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CrossConnectService {

    private final CrossConnectRepository repository;
    private final ColoCustomerRepository customerRepository;
    private final CarrierPresenceRepository carrierRepository;
    private final RackRepository rackRepository;
    private final InstalledAssetRepository assetRepository;
    private final NotificationRepository notificationRepository;
    private final AuditLogRepository auditLogRepository;

    public CrossConnectService(
            CrossConnectRepository repository,
            ColoCustomerRepository customerRepository,
            CarrierPresenceRepository carrierRepository,
            RackRepository rackRepository,
            InstalledAssetRepository assetRepository, 
            NotificationRepository notificationRepository, 
            AuditLogRepository auditLogRepository) {

        this.repository = repository;
        this.customerRepository = customerRepository;
        this.carrierRepository = carrierRepository;
        this.rackRepository = rackRepository;
        this.assetRepository = assetRepository;
        this.notificationRepository = notificationRepository;
        this.auditLogRepository = auditLogRepository;
    }

    //  CREATE
    public CrossConnect create(CrossConnect entity) {

        validate(entity);
        return repository.save(entity);
    }

    //  GET ALL
    public List<CrossConnect> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public CrossConnect findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CrossConnect", id));
    }

    //  UPDATE
    public CrossConnect update(Long id, CrossConnect s) {

        CrossConnect existing = findById(id);

        existing.setCustomerId(s.getCustomerId());
        existing.setRackId(s.getRackId());
        existing.setAssetId(s.getAssetId());
        existing.setConnectionType(s.getConnectionType());
        existing.setPortA(s.getPortA());
        existing.setPortZ(s.getPortZ());
        existing.setBandwidth(s.getBandwidth());
        existing.setCarrierId(s.getCarrierId());
        existing.setOrderDate(s.getOrderDate());
        existing.setProvisionedDate(s.getProvisionedDate());
        existing.setMonthlyCost(s.getMonthlyCost());
        existing.setStatus(s.getStatus());

        validate(existing);
        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION (unchanged logic )
    private void validate(CrossConnect entity) {

        if (!customerRepository.existsById(entity.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", entity.getCustomerId());
        }

        if (entity.getCarrierId() != null &&
                !carrierRepository.existsById(entity.getCarrierId())) {
            throw new ResourceNotFoundException("CarrierPresence", entity.getCarrierId());
        }

        if (entity.getRackId() != null &&
                !rackRepository.existsById(entity.getRackId())) {
            throw new ResourceNotFoundException("Rack", entity.getRackId());
        }

        if (entity.getAssetId() != null) {

            InstalledAsset asset = assetRepository.findById(entity.getAssetId())
                    .orElseThrow(() -> new ResourceNotFoundException("InstalledAsset", entity.getAssetId()));

            if (entity.getRackId() != null &&
                    !entity.getRackId().equals(asset.getRackId())) {
                throw new IllegalArgumentException("Asset does not belong to rack");
            }

            if (entity.getCustomerId() != null &&
                    asset.getCustomerId() != null &&
                    !entity.getCustomerId().equals(asset.getCustomerId())) {
                throw new IllegalArgumentException("Customer mismatch with asset");
            }
        }

        if (entity.getPortA() != null &&
                entity.getPortA().equals(entity.getPortZ())) {
            throw new IllegalArgumentException("portA and portZ cannot be same");
        }

        if(entity.getProvisionedDate() != null && entity.getOrderDate() != null && entity.getProvisionedDate().isBefore(entity.getOrderDate())){
            throw new IllegalArgumentException("Provisioned date cannot be before order date");
        }
    }

    public CrossConnect changeStatus(Long id, CrossConnectStatus status) {
        CrossConnect crossConnect = findById(id);
        crossConnect.setStatus(status);
        if (status == CrossConnectStatus.PROVISIONED) {
            crossConnect.setProvisionedDate(LocalDate.now());
        }
        CrossConnect saved = repository.save(crossConnect);
        Notification notification = new Notification();
        notification.setUserId(saved.getCustomerId());
        notification.setMessage("Cross Connect " + saved.getCrossConnectId() + " changed to " + status);
        notificationRepository.save(notification);
        AuditLog audit = new AuditLog();
        audit.setAction(AuditAction.STATUS_CHANGE);
        audit.setEntityType(EntityType.CONNECTIVITY);
        audit.setRecordId(saved.getCrossConnectId());
        auditLogRepository.save(audit);
        return saved;
    }
 

    //  SEARCH
    public List<CrossConnect> findByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<CrossConnect> findByStatus(CrossConnectStatus status) {
        return repository.findByStatus(status);
    }
}