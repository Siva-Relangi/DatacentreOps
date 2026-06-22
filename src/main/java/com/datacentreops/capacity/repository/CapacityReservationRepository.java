package com.datacentreops.capacity.repository;

import com.datacentreops.capacity.entity.CapacityReservation;
import com.datacentreops.capacity.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapacityReservationRepository extends JpaRepository<CapacityReservation, Long> {

    List<CapacityReservation> findByCustomerId(Long customerId);

    List<CapacityReservation> findByHallId(Long hallId);

    List<CapacityReservation> findByStatus(ReservationStatus status);
}