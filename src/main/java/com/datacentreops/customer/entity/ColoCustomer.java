package com.datacentreops.customer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "colo_customer")
@Getter
@Setter
@NoArgsConstructor
public class ColoCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @NotBlank
    private String companyName;

    private String industrySegment;

    private String contactPerson;

    private Long accountManagerId;

    private String kycStatus;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.ACTIVE;
}