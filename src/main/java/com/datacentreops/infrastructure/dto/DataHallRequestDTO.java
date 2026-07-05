package com.datacentreops.infrastructure.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataHallRequestDTO {

    @NotNull(message = "Data Centre id is required")
    private Long dataCentreId;

    @NotBlank(message = "Hall Name is required")
    @Size(max = 100)
    private String hallName;

    @NotNull(message = "Total Racks is required")
    @Positive
    private Integer totalRacks;

    @NotNull(message = "Total Power is required")
    @Positive
    private Double totalPowerKW;

    @NotNull(message = "Cooling Capacity is required")
    @Positive
    private Double coolingCapacityKW;

    @NotBlank(message = "Tier Level is required")
    private String tierLevel;
}