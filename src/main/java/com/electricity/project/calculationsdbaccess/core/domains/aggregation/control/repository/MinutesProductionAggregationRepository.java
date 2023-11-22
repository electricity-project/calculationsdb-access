package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.MinutesAggregation;
import org.springframework.stereotype.Repository;

@Repository
public interface MinutesProductionAggregationRepository extends PowerProductionAggregationRepository<MinutesAggregation> {
    @Override
    default AggregationPeriodType getAggregationEnum() {
        return AggregationPeriodType.MINUTE;
    }
}

