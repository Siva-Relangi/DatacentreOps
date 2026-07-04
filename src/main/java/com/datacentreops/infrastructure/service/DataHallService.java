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

        validate(hall);
        if(repository.existsByHallNameAndDataCentreId(hall.getHallName(), hall.getDataCentreId())){
            throw new IllegalArgumentException("Hall already exists in this Data Centre");
        }
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

    private void validate(DataHall hall){
        if(hall.getHallName() == null || hall.getHallName().isBlank()){
            throw new IllegalArgumentException("Hall name is required");
        }

        if(hall.getTotalRacks() == null || hall.getTotalRacks() <= 0){
            throw new IllegalArgumentException("Total racks must be greater than 0");
        }

        if(hall.getTotalPowerKW() == null || hall.getTotalPowerKW() <= 0){
            throw new IllegalArgumentException("Total power must be greater than 0");
        }

        if(hall.getCoolingCapacityKW() == null || hall.getCoolingCapacityKW() <= 0){
            throw new IllegalArgumentException("cooling capacity must be greater than 0");
        }

        if(hall.getTierLevel() == null || hall.getTierLevel().isBlank()){
            throw new IllegalArgumentException("Tier Level is required");
        }
    }

    //  UPDATE
    public DataHall update(Long id, DataHall h) {

        DataHall existing = findById(id);
        validate(h);
        existing.setDataCentreId(h.getDataCentreId());
        existing.setHallName(h.getHallName());
        existing.setTotalRacks(h.getTotalRacks());
        existing.setTotalPowerKW(h.getTotalPowerKW());
        existing.setCoolingCapacityKW(h.getCoolingCapacityKW());
        existing.setTierLevel(h.getTierLevel());
        existing.setStatus(h.getStatus());

        if(!existing.getHallName().equals(h.getHallName()) && repository.existsByHallNameAndDataCentreId(h.getHallName(), h.getDataCentreId())){
            throw new IllegalArgumentException("Hall already exists in this Data Centre");
        }

        int existingRackCount = rackRepository.findByHallId(id).size();
        if(existing.getTotalRacks() < existingRackCount){
            throw new IllegalArgumentException("Total racks cannot be less than existing racks");
        }
        return repository.save(existing);
    }

    //  DELETE (BUSINESS RULE )
    public void delete(Long id) {

        if (!rackRepository.findByHallId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete hall " + id + ": it still has racks");
        }

        findById(id);
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