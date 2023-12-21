package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository.PowerProductionAggregationRepository;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.DaysAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.HoursAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.MinutesAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import com.electricity.project.calculationsdbaccess.infrastructure.FilterDateParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PowerProductionAggregationService {

    private final Map<AggregationPeriodType, PowerProductionAggregationRepository<? extends PowerProductionAggregation>> aggregationMap;

    public List<PowerProductionAggregation> getLastRecords(AggregationPeriodType periodType, Integer duration) {
        List<PowerProductionAggregation> productionAggregations = aggregationMap.get(periodType)
                .findByOrderByTimestampAsc(FilterDateParser.createFilterDate(periodType, duration))
                .stream()
                .map(PowerProductionAggregation.class::cast)
                .toList();

        if (productionAggregations.size() == duration) {
            return productionAggregations;
        }

        return fillMissingTimestamps(periodType, duration, productionAggregations);
    }

    private List<PowerProductionAggregation> fillMissingTimestamps(AggregationPeriodType periodType, Integer duration,
                                                                   List<PowerProductionAggregation> productionAggregations) {
        List<PowerProductionAggregation> resultList = fillMissingValuesBetween(periodType, productionAggregations);

        if (resultList.size() != duration) {
            return fillMissingValuesBefore(duration, periodType, resultList);
        }

        return resultList;
    }

    private List<PowerProductionAggregation> fillMissingValuesBetween(AggregationPeriodType periodType,
                                                                      List<PowerProductionAggregation> productionAggregations) {
        List<PowerProductionAggregation> resultList = new LinkedList<>();
        int i = 0;

        LocalDateTime lastDateInProductionAggregation = productionAggregations.getLast().getTimestamp();
        for (LocalDateTime timestamp = productionAggregations.getFirst().getTimestamp();
             timestamp.isAfter(lastDateInProductionAggregation) || timestamp.isEqual(lastDateInProductionAggregation);
             timestamp = parseTime(timestamp, periodType)) {

            PowerProductionAggregation powerProductionAggregation = productionAggregations.get(i);
            if (!powerProductionAggregation.getTimestamp().equals(timestamp)) {
                resultList.add(buildEmptyAggregation(timestamp, periodType));
            } else {
                resultList.add(powerProductionAggregation);
                i++;
            }
        }
        return resultList;
    }

    private LinkedList<PowerProductionAggregation> fillMissingValuesBefore(Integer duration, AggregationPeriodType periodType, List<PowerProductionAggregation> resultList) {
        LinkedList<PowerProductionAggregation> resultsBefore = new LinkedList<>();
        LocalDateTime firstTimestampInResultList = resultList.getFirst().getTimestamp();

        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        for (LocalDateTime timestamp = now;
             timestamp.isAfter(firstTimestampInResultList);
             timestamp = parseTime(timestamp, periodType)) {
            resultsBefore.add(buildEmptyAggregation(timestamp, periodType));
        }
        resultsBefore.addAll(resultList);

        if (resultsBefore.size() > duration) {
            for (int i = 0; i < resultsBefore.size() - duration; i++) {
                resultsBefore.removeLast();
            }
        }

        return resultsBefore;
    }

    private PowerProductionAggregation buildEmptyAggregation(LocalDateTime timestamp, AggregationPeriodType periodType) {
        return switch (periodType) {
            case MINUTE -> MinutesAggregation.builder()
                    .aggregatedPowerStations(null)
                    .aggregationValue(null)
                    .timestamp(timestamp)
                    .build();
            case HOUR -> HoursAggregation.builder()
                    .aggregatedPowerStations(null)
                    .aggregationValue(null)
                    .timestamp(timestamp)
                    .build();
            case DAY -> DaysAggregation.builder()
                    .aggregatedPowerStations(null)
                    .aggregationValue(null)
                    .timestamp(timestamp)
                    .build();
        };
    }

    private static LocalDateTime parseTime(LocalDateTime timestamp, AggregationPeriodType periodType) {
        return switch (periodType) {
            case MINUTE -> timestamp.minusMinutes(1);
            case HOUR -> timestamp.minusHours(1);
            case DAY -> timestamp.minusDays(1);
        };
    }

}
