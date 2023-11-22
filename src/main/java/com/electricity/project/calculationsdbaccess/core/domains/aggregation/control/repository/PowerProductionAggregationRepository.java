package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PowerProductionAggregationRepository<T extends PowerProductionAggregation> extends JpaRepository<T, Long>, AggregationEnumBean {
    @Query("select p from #{#entityName} p where p.timestamp >=:filterDate order by p.timestamp DESC")
    List<T> findByOrderByTimestampAsc(LocalDateTime filterDate);

    @Override
    default AggregationPeriodType getAggregationEnum() {
        return null;
    }
}
