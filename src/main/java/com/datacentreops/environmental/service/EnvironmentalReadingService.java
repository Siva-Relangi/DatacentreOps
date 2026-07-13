package com.datacentreops.environmental.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.environmental.entity.*;
import com.datacentreops.iam.entity.*;
import com.datacentreops.notification.entity.*;
import com.datacentreops.environmental.repository.EnvironmentalReadingRepository;
import com.datacentreops.iam.repository.AuditLogRepository;
import com.datacentreops.environmental.repository.EnvironmentalIncidentRepository;
import com.datacentreops.infrastructure.repository.DataHallRepository;
import com.datacentreops.notification.repository.NotificationRepository;
import com.datacentreops.iam.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalReadingService {

    private final EnvironmentalReadingRepository repository;
    private final DataHallRepository dataHallRepository;
    private final EnvironmentalIncidentRepository incidentRepository;
    private final NotificationRepository notificationRepository;
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public EnvironmentalReadingService(EnvironmentalReadingRepository repository,
                                       DataHallRepository dataHallRepository,
                                       EnvironmentalIncidentRepository incidentRepository,
                                       NotificationRepository notificationRepository,
                                       AuditLogRepository auditLogRepository,
                                       UserRepository userRepository) {
        this.repository = repository;
        this.dataHallRepository = dataHallRepository;
        this.incidentRepository = incidentRepository;
        this.notificationRepository = notificationRepository;
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    //  CREATE
    public EnvironmentalReading create(EnvironmentalReading reading) {
        validate(reading);
        if (reading.getReadingType() == ReadingType.TEMPERATURE && reading.getValue() != null) {
            
            if (reading.getValue() >= 40) {
                reading.setStatus(ReadingStatus.CRITICAL);
            }
        
            else if (reading.getValue() >= 35) {
                reading.setStatus(ReadingStatus.WARNING);
            }

        }

        if (reading.getReadingType() == ReadingType.HUMIDITY && reading.getValue() != null) {
            if (reading.getValue() >= 85) {
                reading.setStatus(ReadingStatus.CRITICAL);
            }
            else if (reading.getValue() >= 70) {
                reading.setStatus(ReadingStatus.WARNING);
            }
        }
        EnvironmentalReading saved = repository.save(reading);

        if (saved.getStatus() == ReadingStatus.CRITICAL) {
            EnvironmentalIncident incident = new EnvironmentalIncident();
            incident.setHallId(saved.getHallId());

            if (saved.getReadingType() == ReadingType.TEMPERATURE) {
                incident.setIncidentType(IncidentType.TEMPERATURE_EXCURSION);
            }

            if (saved.getReadingType() == ReadingType.HUMIDITY) {
                incident.setIncidentType(IncidentType.HUMIDITY_ALARM);
            }
            incident.setImpactSummary("Auto generated from sensor " + saved.getSensorId() + " value=" + saved.getValue());
            incidentRepository.save(incident);

            List<User> admins = userRepository.findByRole(Role.ADMIN);

            if (admins.isEmpty()) {
                throw new ResourceNotFoundException("Admin", 0L);
            }

            User admin = userRepository.findByRole(Role.ADMIN)
                        .stream()
                        .filter(user -> user.getStatus() == UserStatus.ACTIVE)
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No active admin found"));

            Notification notification = new Notification();
            notification.setUserId(admin.getUserId());
            notification.setCategory(NotificationCategory.ENVIRONMENTAL);
            notification.setMessage(
                    "Critical Environmental Incident detected in Hall " + saved.getHallId());

            notificationRepository.save(notification);

            AuditLog audit = new AuditLog();
            audit.setAction(AuditAction.CREATE);
            audit.setEntityType(EntityType.ENVIRONMENTAL);
            audit.setRecordId(incident.getIncidentId());
            auditLogRepository.save(audit);
        }
        return saved;
    }


    //  GET ALL
    public List<EnvironmentalReading> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public EnvironmentalReading findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EnvironmentalReading", id));
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  VALIDATION (NOW ENUM-BASED )
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

    //  SEARCH
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