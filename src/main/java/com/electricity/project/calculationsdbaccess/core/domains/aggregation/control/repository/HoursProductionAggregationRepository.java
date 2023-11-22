package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.HoursAggregation;
import org.springframework.stereotype.Repository;

@Repository
public interface HoursProductionAggregationRepository extends PowerProductionAggregationRepository<HoursAggregation> {
    @Override
    default AggregationPeriodType getAggregationEnum() {
        return AggregationPeriodType.HOUR;
    }
}
