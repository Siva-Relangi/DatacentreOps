package com.datacentreops.infrastructure.repository;

import com.datacentreops.infrastructure.entity.InstalledAsset;
import com.datacentreops.infrastructure.entity.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstalledAssetRepository extends JpaRepository<InstalledAsset, Long> {

    List<InstalledAsset> findByRackId(Long rackId);

    List<InstalledAsset> findByCustomerId(Long customerId);

    List<InstalledAsset> findByAssetType(String assetType);

    List<InstalledAsset> findByStatus(AssetStatus status);
}