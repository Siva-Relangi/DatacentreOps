package com.datacentreops.environmental.dto;

import com.datacentreops.environmental.entity.ReadingStatus;
import com.datacentreops.environmental.entity.ReadingType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EnvironmentalReadingResponseDTO {

    private Long readingId;
    private Long hallId;
    private String sensorId;
    private ReadingType readingType;
    private Double value;
    private String unit;
    private LocalDateTime readingTime;
    private ReadingStatus status;
}