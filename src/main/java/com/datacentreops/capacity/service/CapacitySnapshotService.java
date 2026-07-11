package com.datacentreops.capacity.service;

import com.datacentreops.capacity.dto.CapacitySnapshotResponseDTO;
import com.datacentreops.capacity.entity.CapacitySnapshot;
import com.datacentreops.capacity.entity.SnapshotStatus;
import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.infrastructure.entity.DataHall;
import com.datacentreops.infrastructure.entity.Rack;
import com.datacentreops.infrastructure.entity.RackStatus;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import com.datacentreops.capacity.repository.CapacitySnapshotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CapacitySnapshotService {

    private final DataHallRepository hallRepository;
    private final RackRepository rackRepository;
    private final CapacitySnapshotRepository snapshotRepository;

    public CapacitySnapshotService(DataHallRepository hallRepository,
                                   RackRepository rackRepository,
                                   CapacitySnapshotRepository snapshotRepository) {
        this.hallRepository = hallRepository;
        this.rackRepository = rackRepository;
        this.snapshotRepository = snapshotRepository;
    }

    public List<CapacitySnapshotResponseDTO> getAllSnapshots() {

        return hallRepository.findAll()
                .stream()
                .map(hall -> buildSnapshot(hall.getHallId()))
                .toList();
    }

    public CapacitySnapshotResponseDTO getSnapshotByHall(Long hallId) {

        if (!hallRepository.existsById(hallId)) {
            throw new ResourceNotFoundException("DataHall", hallId);
        }

        return buildSnapshot(hallId);
    }

    private CapacitySnapshotResponseDTO buildSnapshot(Long hallId) {

        DataHall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new ResourceNotFoundException("DataHall", hallId));

        List<Rack> racks = rackRepository.findByHallId(hallId);

        int totalRacks = racks.size();

        int allocatedRacks = (int) racks.stream()
                .filter(r -> r.getStatus() == RackStatus.ALLOCATED)
                .count();

        double allocatedPower = racks.stream()
                .mapToDouble(r -> r.getAllocatedPowerKW() == null ? 0 : r.getAllocatedPowerKW())
                .sum();

        double totalPower = hall.getTotalPowerKW();

        double powerUtilisation = totalPower == 0
                ? 0
                : (allocatedPower / totalPower) * 100;

        int totalU = racks.stream()
                .mapToInt(r -> r.getTotalU() == null ? 0 : r.getTotalU())
                .sum();

        int usedU = racks.stream()
                .mapToInt(r -> r.getUsedU() == null ? 0 : r.getUsedU())
                .sum();

        double spaceUtilisation = totalU == 0
                ? 0
                : ((double) usedU / totalU) * 100;

        double coolingUsed = allocatedPower;

        double coolingUtilisation = hall.getCoolingCapacityKW() == 0
                ? 0
                : (coolingUsed / hall.getCoolingCapacityKW()) * 100;


        CapacitySnapshot snapshot = snapshotRepository.findTopByHallIdAndStatusOrderBySnapshotDateDesc(hallId, SnapshotStatus.CURRENT);

        if (snapshot == null) {
            snapshot = new CapacitySnapshot();
            snapshot.setHallId(hallId);
            snapshot.setStatus(SnapshotStatus.CURRENT);
        }

        snapshot.setSnapshotDate(LocalDate.now());
        snapshot.setTotalRacks(totalRacks);
        snapshot.setAllocatedRacks(allocatedRacks);
        snapshot.setTotalPowerKW(totalPower);
        snapshot.setAllocatedPowerKW(allocatedPower);
        snapshot.setCoolingUsagePercent(coolingUtilisation);
        snapshot.setSpaceUtilisationPercent(spaceUtilisation);
        snapshot.setPowerUtilisationPercent(powerUtilisation);

        snapshotRepository.save(snapshot);

        CapacitySnapshotResponseDTO dto = new CapacitySnapshotResponseDTO();

        dto.setHallId(hallId);
        dto.setSnapshotDate(LocalDate.now());

        dto.setTotalRacks(totalRacks);
        dto.setAllocatedRacks(allocatedRacks);

        dto.setTotalPowerKW(totalPower);
        dto.setAllocatedPowerKW(allocatedPower);

        dto.setSnapshotId(snapshot.getSnapshotId());
        dto.setStatus(snapshot.getStatus());
        dto.setPowerUtilisationPercent(powerUtilisation);
        dto.setSpaceUtilisationPercent(spaceUtilisation);
        dto.setCoolingUsagePercent(coolingUtilisation);

        return dto;
    }
}