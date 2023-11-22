package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository.PowerProductionAggregationRepository;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PowerProductionAggregationService {

    private final Map<AggregationPeriodType, PowerProductionAggregationRepository<? extends PowerProductionAggregation>> aggregationMap;

    public List<PowerProductionAggregation> getLastRecords(AggregationPeriodType periodType, Integer duration) {
        return aggregationMap.get(periodType)
                .findByOrderByTimestampAsc(createFilterDate(periodType, duration))
                .stream()
                .map(PowerProductionAggregation.class::cast)
                .toList();
    }

    private static LocalDateTime createFilterDate(AggregationPeriodType periodType, Integer duration) {
        return switch (periodType) {
            case MINUTE -> LocalDateTime.now().minusMinutes(duration);
            case HOUR -> LocalDateTime.now().minusHours(duration);
            case DAY -> LocalDateTime.now().minusDays(duration);
        };
    }

}
