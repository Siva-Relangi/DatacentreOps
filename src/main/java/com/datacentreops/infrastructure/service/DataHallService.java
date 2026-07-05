package com.datacentreops.infrastructure.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.infrastructure.entity.DataHall;
import com.datacentreops.infrastructure.entity.HallStatus;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DataHallService {

    private final DataHallRepository repository;
    private final RackRepository rackRepository;

    public DataHallService(DataHallRepository repository,
                           RackRepository rackRepository) {
        this.repository = repository;
        this.rackRepository = rackRepository;
    }

    //  CREATE
    public DataHall create(DataHall hall) {

        if(repository.existsByHallNameAndDataCentreId(hall.getHallName(), hall.getDataCentreId())){
            throw new IllegalArgumentException("Hall already exists in this Data Centre");
        }
        hall.setHallName(hall.getHallName().trim());
        hall.setStatus(HallStatus.OPERATIONAL);
        return repository.save(hall);
    }

    //  GET ALL
    public List<DataHall> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public DataHall findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DataHall", id));
    }

    //  UPDATE
    public DataHall update(Long id, DataHall hall) {

        DataHall existing = findById(id);
        
        if (!existing.getHallName().equalsIgnoreCase(hall.getHallName())
                && repository.existsByHallNameAndDataCentreId(hall.getHallName().trim(), hall.getDataCentreId())) {

            throw new IllegalArgumentException(
                    "Hall already exists in this Data Centre");
        }

        int existingRackCount = rackRepository.findByHallId(id).size();
        if(existing.getTotalRacks() < existingRackCount){
            throw new IllegalArgumentException("Total racks cannot be less than existing racks");
        }

        existing.setDataCentreId(hall.getDataCentreId());
        existing.setHallName(hall.getHallName().trim());
        existing.setTotalRacks(hall.getTotalRacks());
        existing.setTotalPowerKW(hall.getTotalPowerKW());
        existing.setCoolingCapacityKW(hall.getCoolingCapacityKW());
        existing.setTierLevel(hall.getTierLevel());

        return repository.save(existing);
    }

    // CHANGE STATUS
    public DataHall changeStatus(Long id, HallStatus status) {

        DataHall hall = findById(id);

        if (status == HallStatus.DECOMMISSIONED
            && !rackRepository.findByHallId(id).isEmpty()) {

            throw new IllegalArgumentException(
                    "Cannot decommission a hall that still contains racks.");
        }

        hall.setStatus(status);

        return repository.save(hall);
    }

    //  DELETE (BUSINESS RULE )
    public void delete(Long id) {

        findById(id);

        if (!rackRepository.findByHallId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete hall " + id + ": it still has racks");
        }

        repository.deleteById(id);
    }

    //  SEARCH
    public List<DataHall> findByDataCentre(Long dataCentreId) {
        return repository.findByDataCentreId(dataCentreId);
    }

    public List<DataHall> findByStatus(HallStatus status) {
        return repository.findByStatus(status);
    }
}