package com.datacentreops.environmental.dto;

import com.datacentreops.environmental.entity.ReadingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentalReadingRequestDTO {

    private Long hallId;
    private String sensorId;
    private ReadingType readingType;
    private Double value;
    private String unit;
}
