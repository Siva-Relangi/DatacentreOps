package com.datacentreops.customer.mapper;

import com.datacentreops.customer.dto.*;
import com.datacentreops.customer.entity.ColoContract;

public class ContractMapper {

    public static ColoContract toEntity(ContractRequestDTO dto) {
        ColoContract contract = new ColoContract();
        contract.setCustomerId(dto.getCustomerId());
        contract.setContractType(dto.getContractType());
        contract.setAllocatedRacks(dto.getAllocatedRacks());
        contract.setPowerCommittedKW(dto.getPowerCommittedKW());
        contract.setMonthlyCost(dto.getMonthlyCost());
        contract.setContractStart(dto.getContractStart());
        contract.setContractEnd(dto.getContractEnd());
        contract.setSlaTier(dto.getSlaTier());
        return contract;
    }

    public static ContractResponseDTO toDTO(ColoContract entity) {
        ContractResponseDTO dto = new ContractResponseDTO();
        dto.setContractId(entity.getContractId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setContractType(entity.getContractType());
        dto.setAllocatedRacks(entity.getAllocatedRacks());
        dto.setPowerCommittedKW(entity.getPowerCommittedKW());
        dto.setMonthlyCost(entity.getMonthlyCost());
        dto.setContractStart(entity.getContractStart());
        dto.setContractEnd(entity.getContractEnd());
        dto.setSlaTier(entity.getSlaTier());
        dto.setStatus(entity.getStatus().name());
        return dto;
    }
}