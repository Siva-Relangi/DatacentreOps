
package com.datacentreops.allocation.controller;

import com.datacentreops.allocation.dto.AllocationRequestDTO;
import com.datacentreops.allocation.dto.AllocationResponseDTO;
import com.datacentreops.allocation.service.AllocationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/allocation")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping("/racks")
    public AllocationResponseDTO allocate(@RequestBody AllocationRequestDTO dto) {
        return allocationService.allocate(dto);
    }
}