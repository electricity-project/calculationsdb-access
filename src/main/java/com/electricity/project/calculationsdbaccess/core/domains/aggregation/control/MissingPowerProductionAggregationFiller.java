package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.DaysAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.HoursAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.MinutesAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class MissingPowerProductionAggregationFiller {

    public List<PowerProductionAggregation> fillMissingTimestamps(AggregationPeriodType periodType, Integer duration,
                                                                  List<PowerProductionAggregation> productionAggregations) {
        List<PowerProductionAggregation> resultList = fillMissingValuesBetween(periodType, productionAggregations);

        if (resultList.size() != duration) {
            resultList = fillMissingValuesBefore(duration, periodType, resultList);
        }

        if (resultList.size() != duration) {
            return fillMissingValuesAfter(duration, periodType, resultList);
        }

        return resultList;
    }

    private List<PowerProductionAggregation> fillMissingValuesBetween(AggregationPeriodType periodType,
                                                                      List<PowerProductionAggregation> productionAggregations) {
        List<PowerProductionAggregation> resultList = new LinkedList<>();
        int i = 0;

        ZonedDateTime lastDateInProductionAggregation = productionAggregations.getLast().getTimestamp();
        for (ZonedDateTime timestamp = productionAggregations.getFirst().getTimestamp();
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

    private List<PowerProductionAggregation> fillMissingValuesBefore(Integer duration, AggregationPeriodType periodType, List<PowerProductionAggregation> resultList) {
        LinkedList<PowerProductionAggregation> resultsBefore = new LinkedList<>();
        ZonedDateTime firstTimestampInResultList = resultList.getFirst().getTimestamp();

        for (ZonedDateTime timestamp = parseDateTimeNow(periodType);
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

    private List<PowerProductionAggregation> fillMissingValuesAfter(Integer duration, AggregationPeriodType periodType, List<PowerProductionAggregation> resultList) {
        int missingValues = duration - resultList.size();
        ZonedDateTime lastDate = resultList.getLast().getTimestamp();

        for (int i = 0; i < missingValues; i++) {
            lastDate = parseTime(lastDate, periodType);
            resultList.add(buildEmptyAggregation(lastDate, periodType));
        }

        return resultList;
    }

    private static ZonedDateTime parseDateTimeNow(AggregationPeriodType periodType) {
        return switch (periodType) {
            case MINUTE -> ZonedDateTime.now().withSecond(0).withNano(0);
            case HOUR -> ZonedDateTime.now().withMinute(0).withSecond(0).withNano(0);
            case DAY -> ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        };
    }

    private PowerProductionAggregation buildEmptyAggregation(ZonedDateTime timestamp, AggregationPeriodType periodType) {
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

    private static ZonedDateTime parseTime(ZonedDateTime timestamp, AggregationPeriodType periodType) {
        return switch (periodType) {
            case MINUTE -> timestamp.minusMinutes(1);
            case HOUR -> timestamp.minusHours(1);
            case DAY -> timestamp.minusDays(1);
        };
    }
}