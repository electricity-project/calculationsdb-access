package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository.PowerProductionAggregationRepository;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import com.electricity.project.calculationsdbaccess.infrastructure.FilterDateParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PowerProductionAggregationService {

    private final Map<AggregationPeriodType, PowerProductionAggregationRepository<? extends PowerProductionAggregation>> aggregationMap;
    private final MissingPowerProductionAggregationFiller missingPowerProductionAggregationFiller;

    public List<PowerProductionAggregation> getLastRecords(AggregationPeriodType periodType, Integer duration) {
        List<PowerProductionAggregation> productionAggregations = aggregationMap.get(periodType)
                .findByOrderByTimestampAsc(FilterDateParser.createFilterDate(periodType, duration))
                .stream()
                .map(PowerProductionAggregation.class::cast)
                .toList();

        if (productionAggregations.size() == duration) {
            return productionAggregations;
        }

        return missingPowerProductionAggregationFiller.fillMissingTimestamps(periodType, duration, productionAggregations);
    }
}
