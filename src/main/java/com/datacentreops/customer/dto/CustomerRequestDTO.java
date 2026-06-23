package com.datacentreops.customer.dto;

import lombok.Getter;
import lombok.Setter;
import com.datacentreops.customer.entity.KYCStatus;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CustomerRequestDTO {

    @NotBlank(message = "Company name is required")
    private String companyName;
    private String industrySegment;
    private String contactPerson;
    private Long accountManagerId;
    private KYCStatus kycStatus;
}