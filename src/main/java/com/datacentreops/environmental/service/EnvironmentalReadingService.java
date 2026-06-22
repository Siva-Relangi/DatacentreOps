package com.datacentreops.environmental.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.environmental.entity.*;
import com.datacentreops.environmental.repository.EnvironmentalReadingRepository;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalReadingService {

    private final EnvironmentalReadingRepository repository;
    private final DataHallRepository dataHallRepository;

    public EnvironmentalReadingService(EnvironmentalReadingRepository repository,
                                       DataHallRepository dataHallRepository) {
        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
    }

    // ✅ CREATE
    public EnvironmentalReading create(EnvironmentalReading r) {

        validate(r);
        return repository.save(r);
    }

    // ✅ GET ALL
    public List<EnvironmentalReading> findAll() {
        return repository.findAll();
    }

    // ✅ GET BY ID
    public EnvironmentalReading findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EnvironmentalReading", id));
    }

    // ✅ UPDATE
    public EnvironmentalReading update(Long id, EnvironmentalReading s) {

        EnvironmentalReading existing = findById(id);

        existing.setHallId(s.getHallId());
        existing.setSensorId(s.getSensorId());
        existing.setReadingType(s.getReadingType());
        existing.setValue(s.getValue());
        existing.setUnit(s.getUnit());
        existing.setStatus(s.getStatus());

        validate(existing);
        return repository.save(existing);
    }

    // ✅ DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    // ✅ VALIDATION (NOW ENUM-BASED ✅)
    private void validate(EnvironmentalReading r) {

        if (!dataHallRepository.existsById(r.getHallId())) {
            throw new ResourceNotFoundException("DataHall", r.getHallId());
        }

        if (r.getValue() != null && r.getReadingType() != null) {

            double v = r.getValue();

            switch (r.getReadingType()) {

                case TEMPERATURE -> {
                    if (v < -50 || v > 80) {
                        throw new IllegalArgumentException("Temperature out of range");
                    }
                }

                case HUMIDITY -> {
                    if (v < 0 || v > 100) {
                        throw new IllegalArgumentException("Humidity out of range");
                    }
                }

                default -> {
                    // other types skip strict validation
                }
            }
        }
    }

    // ✅ SEARCH
    public List<EnvironmentalReading> findByHall(Long hallId) {
        return repository.findByHallId(hallId);
    }

    public List<EnvironmentalReading> findByStatus(ReadingStatus status) {
        return repository.findByStatus(status);
    }

    public List<EnvironmentalReading> findByHallAndType(Long hallId, ReadingType type) {
        return repository.findByHallIdAndReadingType(hallId, type);
    }
}