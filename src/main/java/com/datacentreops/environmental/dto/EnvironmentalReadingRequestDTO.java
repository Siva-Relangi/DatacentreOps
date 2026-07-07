package com.datacentreops.environmental.dto;

import com.datacentreops.environmental.entity.ReadingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentalReadingRequestDTO {

    @NotNull(message = "Hall is required")
    private Long hallId;

    @NotBlank(message = "Sensor ID is required")
    private String sensorId;

    @NotNull(message = "Reading Type is required")
    private ReadingType readingType;

    @Positive(message = "Reading value must be greater than 0")
    private Double value;

    @NotBlank(message = "unit is required")
    private String unit;
}
