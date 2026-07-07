package com.datacentreops.environmental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "environmental_incident")
@Getter
@Setter
@NoArgsConstructor
public class EnvironmentalIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "incident_id")
    private Long incidentId;

    @Column(nullable = false)
    private Long hallId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IncidentType incidentType;

    private LocalDateTime startTime = LocalDateTime.now();

    private LocalDateTime resolvedTime;

    private Long assignedEngineerId;

    @Column(length = 2000)
    private String impactSummary;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status = IncidentStatus.ACTIVE;
}