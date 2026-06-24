package com.datacentreops.allocation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllocationRequestDTO {
    private Long contractId;
    private Long hallId;
    private Long userId;
}
