package com.datacentreops.infrastructure.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.infrastructure.entity.AssetStatus;
import com.datacentreops.infrastructure.entity.InstalledAsset;
import com.datacentreops.infrastructure.repository.InstalledAssetRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import com.datacentreops.infrastructure.entity.Rack;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstalledAssetService {

    private final InstalledAssetRepository repository;
    private final RackRepository rackRepository;
    private final ColoCustomerRepository customerRepository;

    public InstalledAssetService(
            InstalledAssetRepository repository,
            RackRepository rackRepository,
            ColoCustomerRepository customerRepository) {

        this.repository = repository;
        this.rackRepository = rackRepository;
        this.customerRepository = customerRepository;
    }

    //  CREATE
    public InstalledAsset create(InstalledAsset asset) {

        validate(asset);
        return repository.save(asset);
    }

    //  GET ALL
    public List<InstalledAsset> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public InstalledAsset findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InstalledAsset", id));
    }

    //  UPDATE
    public InstalledAsset update(Long id, InstalledAsset a) {

        InstalledAsset existing = findById(id);

        existing.setRackId(a.getRackId());
        existing.setCustomerId(a.getCustomerId());
        existing.setAssetType(a.getAssetType());
        existing.setMake(a.getMake());
        existing.setModel(a.getModel());
        existing.setSerialNumber(a.getSerialNumber());
        existing.setUPosition(a.getUPosition());
        existing.setUHeight(a.getUHeight());
        existing.setPowerDrawW(a.getPowerDrawW());
        existing.setInstalledDate(a.getInstalledDate());
        existing.setStatus(a.getStatus());

        validate(existing);
        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION (IMPORTANT 🔥)
    private void validate(InstalledAsset asset) {

        if (asset.getCustomerId() != null &&
                !customerRepository.existsById(asset.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", asset.getCustomerId());
        }

        Rack rack = rackRepository.findById(asset.getRackId())
                .orElseThrow(() -> new ResourceNotFoundException("Rack", asset.getRackId()));

        if (rack.getTotalU() != null && asset.getUHeight() != null) {

            int used = repository.findByRackId(asset.getRackId()).stream()
                    .filter(a -> a.getUHeight() != null)
                    .mapToInt(InstalledAsset::getUHeight)
                    .sum();

            if (used + asset.getUHeight() > rack.getTotalU()) {
                throw new IllegalArgumentException("Not enough rack U space");
            }
        }
    }

    //  SEARCH
    public List<InstalledAsset> findByRack(Long rackId) {
        return repository.findByRackId(rackId);
    }

    public List<InstalledAsset> findByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<InstalledAsset> findByType(String assetType) {
        return repository.findByAssetType(assetType);
    }

    public List<InstalledAsset> findByStatus(AssetStatus status) {
        return repository.findByStatus(status);
    }
}