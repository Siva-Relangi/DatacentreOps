package com.datacentreops.connectivity.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.connectivity.entity.CarrierPresence;
import com.datacentreops.connectivity.entity.CarrierStatus;
import com.datacentreops.connectivity.repository.CarrierPresenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrierPresenceService {

    private final CarrierPresenceRepository repository;

    public CarrierPresenceService(CarrierPresenceRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public CarrierPresence create(CarrierPresence entity) {
        return repository.save(entity);
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

        existing.setCarrierName(c.getCarrierName());
        existing.setServiceType(c.getServiceType());
        existing.setDataCentreId(c.getDataCentreId());
        existing.setMmrCabinetId(c.getMmrCabinetId());
        existing.setStatus(c.getStatus());

        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
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