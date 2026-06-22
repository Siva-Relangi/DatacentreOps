package com.datacentreops.customer.repository;

import com.datacentreops.customer.entity.ColoCustomer;
import com.datacentreops.customer.entity.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColoCustomerRepository extends JpaRepository<ColoCustomer, Long> {
    List<ColoCustomer> findByStatus(CustomerStatus status);
    List<ColoCustomer> findByAccountManagerId(Long accountManagerId);
    List<ColoCustomer> findByIndustrySegment(String industrySegment);
}
