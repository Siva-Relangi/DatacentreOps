package com.datacentreops.allocation.service;
import com.datacentreops.allocation.dto.AllocationRequestDTO;
import com.datacentreops.allocation.dto.AllocationResponseDTO;
import com.datacentreops.capacity.entity.CapacityReservation;
import com.datacentreops.capacity.entity.CapacitySnapshot;
import com.datacentreops.capacity.entity.ReservationStatus;
import com.datacentreops.capacity.entity.SnapshotStatus;
import com.datacentreops.capacity.repository.CapacityReservationRepository;
import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.entity.ColoContract;
import com.datacentreops.customer.entity.ContractStatus;
import com.datacentreops.customer.repository.ColoContractRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.entity.EntityType;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.infrastructure.entity.Rack;
import com.datacentreops.infrastructure.entity.RackStatus;
import com.datacentreops.infrastructure.repository.RackRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AllocationService {
    private final RackRepository rackRepository;
    private final ColoContractRepository contractRepository;
    private final CapacityReservationRepository reservationRepository;
    private final AuditLogRepository auditLogRepository;

    public AllocationService(
        RackRepository rackRepository,
        ColoContractRepository contractRepository,
        CapacityReservationRepository reservationRepository,
        AuditLogRepository auditLogRepository) {

        this.rackRepository = rackRepository;
        this.contractRepository = contractRepository;
        this.reservationRepository = reservationRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public AllocationResponseDTO allocate(AllocationRequestDTO request) {
        ColoContract contract = contractRepository.findById(request.getContractId())
        .orElseThrow(() -> new ResourceNotFoundException("Contract", request.getContractId()));
        if (contract.getStatus() != ContractStatus.ACTIVE) {
            throw new IllegalStateException("Only ACTIVE contracts can be allocated");
        }
        Long customerId = contract.getCustomerId();
        Integer rackCount = contract.getAllocatedRacks();
        Double powerKW = contract.getPowerCommittedKW();
        List<Rack> availableRacks = rackRepository.findAll().stream().filter(r -> request.getHallId()
                                                .equals(r.getHallId()) && r.getStatus() == RackStatus.AVAILABLE).limit(rackCount).toList();
        if (availableRacks.size() < rackCount) {
            throw new IllegalStateException("Not enough racks available. Requested: " + rackCount + ", Available: " + availableRacks.size());
        }
        for (Rack rack : availableRacks) {
            rack.setCustomerId(customerId);
            rack.setStatus(RackStatus.ALLOCATED);
            if (rack.getUsedU() == null) {
                rack.setUsedU(0);
            }
            if (rack.getAvailableU() == null && rack.getTotalU() != null) {
                rack.setAvailableU(rack.getTotalU());
            }
            rackRepository.save(rack);
        }

        CapacityReservation reservation = new CapacityReservation();
        reservation.setCustomerId(customerId);
        reservation.setHallId(request.getHallId());
        reservation.setReservedPowerKW(powerKW);
        reservation.setReservedU(rackCount * 42);
        reservation.setExpiryDate(contract.getContractEnd());
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservationRepository.save(reservation);
        contract.setStatus(ContractStatus.ALLOCATED);

        AuditLog log = new AuditLog();
        log.setUserId(request.getUserId());     
        log.setAction(AuditAction.ALLOCATE);
        log.setEntityType(EntityType.RACK);
        log.setRecordId(customerId);
        auditLogRepository.save(log);
        return new AllocationResponseDTO(customerId,contract.getContractId(),rackCount, powerKW, "Racks allocated successfully");
    }
}
 

 
 