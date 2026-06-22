package com.datacentreops.connectivity.repository;

import com.datacentreops.connectivity.entity.CarrierPresence;
import com.datacentreops.connectivity.entity.CarrierStatus;
import com.datacentreops.connectivity.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrierPresenceRepository extends JpaRepository<CarrierPresence, Long> {

    List<CarrierPresence> findByDataCentreId(Long dataCentreId);

    List<CarrierPresence> findByStatus(CarrierStatus status);

    List<CarrierPresence> findByServiceType(ServiceType serviceType);
}
