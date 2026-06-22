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

    @NotNull
    private Long hallId;

    private String sensorId;

    @Enumerated(EnumType.STRING)
    private ReadingType readingType;

    @Column(name = "reading_value")
    private Double value;

    private String unit;

    private LocalDateTime readingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ReadingStatus status = ReadingStatus.NORMAL;
}