package com.datacentreops.customer.dto;

import lombok.Getter;
import lombok.Setter;
import com.datacentreops.customer.entity.KycStatus;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CustomerRequestDTO {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Industry Segment is required")
    private String industrySegment;

    @NotBlank(message = "Contract Person is required")
    private String contactPerson;

    private Long accountManagerId;

    private KycStatus kycStatus;
}