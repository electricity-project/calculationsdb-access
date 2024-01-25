package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.DaysAggregation;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysProductionAggregationRepository extends PowerProductionAggregationRepository<DaysAggregation> {
    @Override
    default AggregationPeriodType getAggregationEnum() {
        return AggregationPeriodType.DAY;
    }
}
