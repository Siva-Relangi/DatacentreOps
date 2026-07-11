package com.datacentreops.customer.service;

import com.datacentreops.capacity.entity.CapacityReservation;
import com.datacentreops.capacity.entity.ReservationStatus;
import com.datacentreops.capacity.repository.CapacityReservationRepository;
import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.entity.ColoContract;
import com.datacentreops.customer.entity.ColoCustomer;
import com.datacentreops.customer.entity.ContractStatus;
import com.datacentreops.customer.repository.ColoContractRepository;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.entity.EntityType;
import com.datacentreops.iam.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColoContractService {

    private final ColoContractRepository repository;
    private final ColoCustomerRepository customerRepository;
    private final AuditLogRepository auditLogRepository;
    private final CapacityReservationRepository reservationRepository;

    public ColoContractService(ColoContractRepository repository,
                               ColoCustomerRepository customerRepository,
                               AuditLogRepository auditLogRepository,
                               CapacityReservationRepository reservationRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.auditLogRepository = auditLogRepository;
        this.reservationRepository = reservationRepository;
    }

    //  CREATE
    public ColoContract create(ColoContract entity) {

        if (!customerRepository.existsById(entity.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", entity.getCustomerId());
        }
        validate(entity);

        // AuditLog
        ColoContract saved = repository.save(entity);
        AuditLog log = new AuditLog();
        log.setAction(AuditAction.CREATE);
        log.setEntityType(EntityType.CUSTOMER);
        log.setRecordId(saved.getContractId());
        auditLogRepository.save(log);

        return saved;
    }

    private void validate(ColoContract entity) {

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

        validate(existing);

        AuditLog log = new AuditLog();
        log.setAction(AuditAction.UPDATE);
        log.setEntityType(EntityType.CUSTOMER);
        log.setRecordId(entity.getContractId());
        auditLogRepository.save(log);

        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {

        ColoContract contract = findById(id);

        List<CapacityReservation> reservations =
                reservationRepository.findByCustomerId(contract.getCustomerId());

        for (CapacityReservation reservation : reservations) {
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(reservation);
        }

        AuditLog log = new AuditLog();
        log.setAction(AuditAction.DELETE);
        log.setEntityType(EntityType.CUSTOMER);
        log.setRecordId(contract.getContractId());
        auditLogRepository.save(log);

        repository.delete(contract);
    }

    public List<ColoContract> findByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<ColoContract> findByStatus(ContractStatus status) {
        return repository.findByStatus(status);
    }
}