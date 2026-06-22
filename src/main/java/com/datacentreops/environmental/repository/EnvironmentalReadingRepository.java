package com.datacentreops.environmental.repository;

import com.datacentreops.environmental.entity.EnvironmentalReading;
import com.datacentreops.environmental.entity.ReadingStatus;
import com.datacentreops.environmental.entity.ReadingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvironmentalReadingRepository extends JpaRepository<EnvironmentalReading, Long> {

    List<EnvironmentalReading> findByHallId(Long hallId);

    List<EnvironmentalReading> findByReadingType(ReadingType readingType);

    List<EnvironmentalReading> findByStatus(ReadingStatus status);

    List<EnvironmentalReading> findByHallIdAndReadingType(Long hallId, ReadingType readingType);
}