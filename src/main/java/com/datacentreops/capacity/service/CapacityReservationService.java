package com.datacentreops.capacity.service;

import com.datacentreops.capacity.entity.CapacityReservation;
import com.datacentreops.capacity.entity.ReservationStatus;
import com.datacentreops.capacity.repository.CapacityReservationRepository;
import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.customer.repository.ColoCustomerRepository;
import com.datacentreops.infrastructure.entity.DataHall;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapacityReservationService {

    private final CapacityReservationRepository repository;

    public CapacityReservationService(
            CapacityReservationRepository repository) {

        this.repository = repository;
    }

    // GET ALL
    public List<CapacityReservation> findAll() {
        return repository.findAll();
    }

    // GET BY ID
    public CapacityReservation findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "CapacityReservation", id));
    }

    // DELETE (Admin Operation)
    public void delete(Long id) {

        CapacityReservation reservation = findById(id);

        repository.delete(reservation);
    }

    // SEARCH

    public List<CapacityReservation> findByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<CapacityReservation> findByHall(Long hallId) {
        return repository.findByHallId(hallId);
    }

    public List<CapacityReservation> findByStatus(ReservationStatus status) {
        return repository.findByStatus(status);
    }
}