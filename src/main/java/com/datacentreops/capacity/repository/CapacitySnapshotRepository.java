package com.datacentreops.capacity.repository;

import com.datacentreops.capacity.entity.CapacitySnapshot;
import com.datacentreops.capacity.entity.SnapshotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapacitySnapshotRepository extends JpaRepository<CapacitySnapshot, Long> {

    List<CapacitySnapshot> findByHallId(Long hallId);
    List<CapacitySnapshot> findByStatus(SnapshotStatus status);
    List<CapacitySnapshot> findByHallIdAndStatus(Long hallId, SnapshotStatus status);
    CapacitySnapshot findTopByHallIdAndStatusOrderBySnapshotDateDesc(Long hallId, SnapshotStatus status);
}