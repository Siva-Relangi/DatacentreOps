package com.datacentreops.allocation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllocationRequestDTO {

    @NotNull(message = "Contract Id is required")
    private Long contractId;

    @NotNull(message = "Hall Id is required")
    private Long hallId;

    @NotNull(message = "User Id is required")
    private Long userId;
}
