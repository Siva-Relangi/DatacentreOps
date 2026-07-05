package com.datacentreops.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "installed_asset")
@Getter
@Setter
@NoArgsConstructor
public class InstalledAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id")
    private Long assetId;

    @Column(nullable = false)
    private Long rackId;

    private Long customerId;

    @Column(nullable = false)
    private String assetType;

    private String make;

    private String model;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    private Integer uPosition;

    @Column(nullable = false)
    private Integer uHeight;

    @Column(nullable = false)
    private Double powerDrawW;

    private LocalDate installedDate;

    @Enumerated(EnumType.STRING)
    private AssetStatus status = AssetStatus.ACTIVE;
}