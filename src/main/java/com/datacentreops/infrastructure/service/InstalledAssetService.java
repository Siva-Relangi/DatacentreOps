package com.datacentreops.infrastructure.service;
import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.iam.entity.AuditAction;
import com.datacentreops.iam.entity.AuditLog;
import com.datacentreops.iam.entity.EntityType;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.infrastructure.entity.AssetStatus;
import com.datacentreops.infrastructure.entity.InstalledAsset;
import com.datacentreops.infrastructure.entity.Rack;
import com.datacentreops.infrastructure.repository.InstalledAssetRepository;
import com.datacentreops.infrastructure.repository.RackRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InstalledAssetService {

    private final InstalledAssetRepository repository;
    private final RackRepository rackRepository;
    private final ColoCustomerRepository customerRepository;
    private final AuditLogRepository auditLogRepository;

    public InstalledAssetService(
        InstalledAssetRepository repository,
        RackRepository rackRepository,
        ColoCustomerRepository customerRepository,
        AuditLogRepository auditLogRepository) {

        this.repository = repository;
        this.rackRepository = rackRepository;
        this.customerRepository = customerRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public InstalledAsset create(InstalledAsset asset) {
        validate(asset);
        Rack rack = rackRepository.findById(asset.getRackId())
                    .orElseThrow(() ->new ResourceNotFoundException("Rack", asset.getRackId()));
        int currentUsedU = rack.getUsedU() == null ? 0 : rack.getUsedU();
        int assetU = asset.getUHeight() == null ? 0 : asset.getUHeight();
        rack.setUsedU(currentUsedU + assetU);
        if (rack.getTotalU() != null) {
            rack.setAvailableU(
            rack.getTotalU() - rack.getUsedU());
        }
        double currentPower = rack.getAllocatedPowerKW() == null ? 0 : rack.getAllocatedPowerKW();
        double assetPower = asset.getPowerDrawW() == null ? 0 : asset.getPowerDrawW() / 1000.0;
        rack.setAllocatedPowerKW(currentPower + assetPower);
        rackRepository.save(rack);
        InstalledAsset saved = repository.save(asset);
        AuditLog log = new AuditLog();
        log.setAction(AuditAction.CREATE);
        log.setEntityType(EntityType.RACK);
        log.setRecordId(saved.getAssetId());
        auditLogRepository.save(log);
        return saved;
    }

    public List<InstalledAsset> findAll() {
        return repository.findAll();
    }

    public InstalledAsset findById(Long id) {
        return repository.findById(id).orElseThrow(() ->new ResourceNotFoundException("InstalledAsset", id));
    }

    public InstalledAsset update(Long id, InstalledAsset asset) {
        InstalledAsset existing = findById(id);
        existing.setRackId(asset.getRackId());
        existing.setCustomerId(asset.getCustomerId());
        existing.setAssetType(asset.getAssetType());
        existing.setMake(asset.getMake());
        existing.setModel(asset.getModel());
        existing.setSerialNumber(asset.getSerialNumber());
        existing.setUPosition(asset.getUPosition());
        existing.setUHeight(asset.getUHeight());
        existing.setPowerDrawW(asset.getPowerDrawW());
        existing.setInstalledDate(asset.getInstalledDate());
        existing.setStatus(asset.getStatus());
        validate(existing);
        return repository.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    private void validate(InstalledAsset asset) {

        if (asset.getCustomerId() != null && !customerRepository.existsById(asset.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", asset.getCustomerId());
        }
        Rack rack = rackRepository.findById(asset.getRackId())
                                .orElseThrow(() -> new ResourceNotFoundException("Rack", asset.getRackId()));

        if (rack.getTotalU() != null && asset.getUHeight() != null) {
            int used = repository.findByRackId(asset.getRackId())
                                .stream().filter(a -> a.getUHeight() != null)
                                .mapToInt(InstalledAsset::getUHeight).sum();

            if (used + asset.getUHeight() > rack.getTotalU()) {
                throw new IllegalArgumentException("Not enough rack U space");
            }
        }
    }

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
    
    