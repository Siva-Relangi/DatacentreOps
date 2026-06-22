package com.datacentreops.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {

    private String companyName;
    private String industrySegment;
    private String contactPerson;
    private Long accountManagerId;
    private String kycStatus;
}