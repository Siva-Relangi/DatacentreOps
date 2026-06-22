package com.datacentreops.connectivity.repository;

import com.datacentreops.connectivity.entity.CrossConnect;
import com.datacentreops.connectivity.entity.CrossConnectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrossConnectRepository extends JpaRepository<CrossConnect, Long> {

    List<CrossConnect> findByCustomerId(Long customerId);

    List<CrossConnect> findByStatus(CrossConnectStatus status);

    List<CrossConnect> findByCarrierId(Long carrierId);
}