package com.datacentreops.connectivity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carrier_presence")
@Getter
@Setter
@NoArgsConstructor
public class CarrierPresence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrier_id")
    private Long carrierId;

    @NotBlank
    private String carrierName;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    private Long dataCentreId;

    private String mmrCabinetId;

    @Enumerated(EnumType.STRING)
    private CarrierStatus status = CarrierStatus.ACTIVE;
}