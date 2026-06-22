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
    private final DataHallRepository dataHallRepository;
    private final ColoCustomerRepository customerRepository;

    public CapacityReservationService(
            CapacityReservationRepository repository,
            DataHallRepository dataHallRepository,
            ColoCustomerRepository customerRepository) {

        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
        this.customerRepository = customerRepository;
    }

    //  CREATE
    public CapacityReservation create(CapacityReservation r) {

        validate(r);
        return repository.save(r);
    }

    //  GET ALL
    public List<CapacityReservation> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public CapacityReservation findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CapacityReservation", id));
    }

    //  UPDATE
    public CapacityReservation update(Long id, CapacityReservation r) {

        CapacityReservation existing = findById(id);

        existing.setCustomerId(r.getCustomerId());
        existing.setHallId(r.getHallId());
        existing.setReservedU(r.getReservedU());
        existing.setReservedPowerKW(r.getReservedPowerKW());
        existing.setReservationDate(r.getReservationDate());
        existing.setExpiryDate(r.getExpiryDate());
        existing.setStatus(r.getStatus());

        validate(existing);
        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION (IMPORTANT)
    private void validate(CapacityReservation r) {

        if (!customerRepository.existsById(r.getCustomerId())) {
            throw new ResourceNotFoundException("ColoCustomer", r.getCustomerId());
        }

        DataHall hall = dataHallRepository.findById(r.getHallId())
                .orElseThrow(() -> new ResourceNotFoundException("DataHall", r.getHallId()));

        if (r.getReservationDate() != null && r.getExpiryDate() != null &&
                r.getExpiryDate().isBefore(r.getReservationDate())) {
            throw new IllegalArgumentException("expiryDate cannot be before reservationDate");
        }

        if (hall.getTotalPowerKW() != null && r.getReservedPowerKW() != null) {

            double reserved = repository.findByHallId(r.getHallId()).stream()
                    .filter(x -> x.getReservedPowerKW() != null)
                    .mapToDouble(CapacityReservation::getReservedPowerKW)
                    .sum();

            if (reserved + r.getReservedPowerKW() > hall.getTotalPowerKW()) {
                throw new IllegalArgumentException(
                        "Power capacity exceeded for hall " + r.getHallId());
            }
        }
    }

    //  SEARCH
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