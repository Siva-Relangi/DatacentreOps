package com.datacentreops.infrastructure.repository;

import com.datacentreops.infrastructure.entity.DataHall;
import com.datacentreops.infrastructure.entity.HallStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataHallRepository extends JpaRepository<DataHall, Long> {

    List<DataHall> findByDataCentreId(Long dataCentreId);

    List<DataHall> findByStatus(HallStatus status);
}