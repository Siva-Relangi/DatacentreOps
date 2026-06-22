package com.datacentreops.customer.dto;

import com.datacentreops.customer.entity.CustomerStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {

    private Long customerId;
    private String companyName;
    private String industrySegment;
    private String contactPerson;
    private Long accountManagerId;
    private String kycStatus;
    private CustomerStatus status;
}