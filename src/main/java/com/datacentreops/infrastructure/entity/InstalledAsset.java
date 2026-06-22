package com.datacentreops.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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

    @NotNull
    private Long rackId;

    private Long customerId;

    private String assetType;

    private String make;

    private String model;

    private String serialNumber;

    @PositiveOrZero
    private Integer uPosition;

    @PositiveOrZero
    private Integer uHeight;

    @PositiveOrZero
    private Double powerDrawW;

    private LocalDate installedDate;

    @Enumerated(EnumType.STRING)
    private AssetStatus status = AssetStatus.ACTIVE;
}