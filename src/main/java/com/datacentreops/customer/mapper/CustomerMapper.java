package com.datacentreops.customer.mapper;

import com.datacentreops.customer.dto.CustomerRequestDTO;
import com.datacentreops.customer.dto.CustomerResponseDTO;
import com.datacentreops.customer.entity.KycStatus;
import com.datacentreops.customer.entity.ColoCustomer;

public class CustomerMapper {

    public static ColoCustomer toEntity(CustomerRequestDTO dto) {
        ColoCustomer c = new ColoCustomer();
        c.setCompanyName(dto.getCompanyName());
        c.setIndustrySegment(dto.getIndustrySegment());
        c.setContactPerson(dto.getContactPerson());
        c.setAccountManagerId(dto.getAccountManagerId());
        c.setKycStatus(dto.getKycStatus() != null ? dto.getKycStatus() : KycStatus.PENDING);
        return c;
    }

    public static CustomerResponseDTO toDTO(ColoCustomer entity) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setCustomerId(entity.getCustomerId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setIndustrySegment(entity.getIndustrySegment());
        dto.setContactPerson(entity.getContactPerson());
        dto.setAccountManagerId(entity.getAccountManagerId());
        dto.setKycStatus(entity.getKycStatus());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}