package com.datacentreops.environmental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "environmental_reading")
@Getter
@Setter
@NoArgsConstructor
public class EnvironmentalReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long readingId;

    @Column(nullable = false)
    private Long hallId;

    @Column(nullable = false)
    private String sensorId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadingType readingType;

    @Column(name = "reading_value", nullable = false)
    private Double value;

    @Column(nullable = false)
    private String unit;

    private LocalDateTime readingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ReadingStatus status = ReadingStatus.NORMAL;
}