package com.datacentreops.infrastructure.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.repository.ColoCustomerRepository;
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
    private final ColoCustomerRepository customerRepository;

    public RackService(
            RackRepository repository,
            DataHallRepository dataHallRepository,
            InstalledAssetRepository assetRepository,
            ColoCustomerRepository customerRepository) {

        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
        this.assetRepository = assetRepository;
        this.customerRepository = customerRepository;
    }

    //  CREATE
    public Rack create(Rack r) {
        validate(r);
        return repository.save(r);
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

        existing.setHallId(r.getHallId());
        existing.setRackLabel(r.getRackLabel());
        existing.setTotalU(r.getTotalU());
        existing.setUsedU(r.getUsedU());
        existing.setAvailableU(r.getAvailableU());
        existing.setMaxPowerKW(r.getMaxPowerKW());
        existing.setAllocatedPowerKW(r.getAllocatedPowerKW());
        existing.setCustomerId(r.getCustomerId());
        existing.setStatus(r.getStatus());

        validate(existing);
        return repository.save(existing);
    }

    //  DELETE (BUSINESS RULE )
    public void delete(Long id) {

        if (!assetRepository.findByRackId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete rack " + id + ": it still has installed assets");
        }

        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION
    private void validate(Rack r) {

        if (!dataHallRepository.existsById(r.getHallId())) {
            throw new ResourceNotFoundException("DataHall", r.getHallId());
        }

        if (r.getCustomerId() != null &&
                !customerRepository.existsById(r.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", r.getCustomerId());
        }

        if (r.getUsedU() != null && r.getTotalU() != null &&
                r.getUsedU() > r.getTotalU()) {
            throw new IllegalArgumentException("usedU cannot exceed totalU");
        }

        if (r.getAllocatedPowerKW() != null && r.getMaxPowerKW() != null &&
                r.getAllocatedPowerKW() > r.getMaxPowerKW()) {
            throw new IllegalArgumentException("allocatedPowerKW cannot exceed maxPowerKW");
        }
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