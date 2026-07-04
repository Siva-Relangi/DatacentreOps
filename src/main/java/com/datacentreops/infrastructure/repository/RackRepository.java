package com.datacentreops.infrastructure.repository;

import com.datacentreops.infrastructure.entity.Rack;
import com.datacentreops.infrastructure.entity.RackStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RackRepository extends JpaRepository<Rack, Long> {

    List<Rack> findByHallId(Long hallId);

    List<Rack> findByCustomerId(Long customerId);

    List<Rack> findByStatus(RackStatus status);

    boolean existsByHallIdAndRackLabel(Long hallId, String rackLabel);

    boolean existsByHallIdAndRackLabelAndRackIdNot(Long hallId, String rackLabel, Long rackId);
}