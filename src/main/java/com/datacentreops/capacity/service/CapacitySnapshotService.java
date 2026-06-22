package com.datacentreops.capacity.service;

import com.datacentreops.capacity.entity.CapacitySnapshot;
import com.datacentreops.capacity.entity.SnapshotStatus;
import com.datacentreops.capacity.repository.CapacitySnapshotRepository;
import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapacitySnapshotService {

    private final CapacitySnapshotRepository repository;
    private final DataHallRepository dataHallRepository;

    public CapacitySnapshotService(CapacitySnapshotRepository repository,
                                   DataHallRepository dataHallRepository) {
        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
    }

    //  CREATE
    public CapacitySnapshot create(CapacitySnapshot snapshot) {

        validate(snapshot);
        return repository.save(snapshot);
    }

    //  GET ALL
    public List<CapacitySnapshot> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public CapacitySnapshot findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CapacitySnapshot", id));
    }

    //  UPDATE
    public CapacitySnapshot update(Long id, CapacitySnapshot s) {

        CapacitySnapshot existing = findById(id);

        existing.setHallId(s.getHallId());
        existing.setSnapshotDate(s.getSnapshotDate());
        existing.setTotalRacks(s.getTotalRacks());
        existing.setAllocatedRacks(s.getAllocatedRacks());
        existing.setTotalPowerKW(s.getTotalPowerKW());
        existing.setAllocatedPowerKW(s.getAllocatedPowerKW());
        existing.setCoolingUsagePercent(s.getCoolingUsagePercent());
        existing.setSpaceUtilisationPercent(s.getSpaceUtilisationPercent());
        existing.setPowerUtilisationPercent(s.getPowerUtilisationPercent());
        existing.setStatus(s.getStatus());

        validate(existing);
        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION
    private void validate(CapacitySnapshot snapshot) {

        if (snapshot.getHallId() == null ||
                !dataHallRepository.existsById(snapshot.getHallId())) {

            throw new ResourceNotFoundException("DataHall", snapshot.getHallId());
        }
    }

    //  SEARCH
    public List<CapacitySnapshot> findByHall(Long hallId) {
        return repository.findByHallId(hallId);
    }

    public List<CapacitySnapshot> findCurrentByHall(Long hallId) {
        return repository.findByHallIdAndStatus(hallId, SnapshotStatus.CURRENT);
    }
}