package com.datacentreops.customer.repository;

import com.datacentreops.customer.entity.ColoContract;
import com.datacentreops.customer.entity.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColoContractRepository extends JpaRepository<ColoContract, Long> {
    List<ColoContract> findByCustomerId(Long customerId);
    List<ColoContract> findByStatus(ContractStatus status);
    List<ColoContract> findByContractType(String contractType);
}
